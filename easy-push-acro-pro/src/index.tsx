import './style/global.less';
import React, { useEffect } from 'react';
import ReactDOM from 'react-dom';
import { createStore } from 'redux';
import { Provider } from 'react-redux';
import { ConfigProvider } from '@arco-design/web-react';
import zhCN from '@arco-design/web-react/es/locale/zh-CN';
import enUS from '@arco-design/web-react/es/locale/en-US';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import rootReducer from './store';
import PageLayout from './layout';
import { GlobalContext } from './context';
import Login from './pages/login';
import checkLogin from './utils/checkLogin';
import changeTheme from './utils/changeTheme';
import useStorage from './utils/useStorage';
import { userResource } from './api/resource';
import { IRoute, staticRoutes } from './routes';
import { XtermPage } from './components/XtermCompenent/xtermPage';
import { getDictDataMap } from './api/dictData';
import { getUserInfo } from './api/oauth';


const store = createStore(rootReducer);

function Index() {
  const [lang, setLang] = useStorage('arco-lang', 'zh-CN');
  const [theme, setTheme] = useStorage('arco-theme', 'light');
  const [dictDataMap, setDictDataMap] = useStorage('dictDataMap','');

  function getArcoLocale() {
    switch (lang) {
      case 'zh-CN':
        return zhCN;
      case 'en-US':
        return enUS;
      default:
        return zhCN;
    }
  }

  function fetchUserInfo() {
    store.dispatch({
      type: 'update-userInfo',
      payload: { userLoading: true },
    });
    getUserInfo().then((res) => {
      const { success, data } = res.data;
      if (success) {
        localStorage.setItem('userInfo', JSON.stringify(data));
        store.dispatch({
          type: 'update-userInfo',
          payload: {
            userInfo: data,
          },
        });
      }
    });
  }

  useEffect(() => {
    if (checkLogin()) {
      fetchUserInfo();
    } 
      //发起请求获取系统路由
      userResource().then((res)=>{
        const {success,data} = res.data
        if(success){
          const side: IRoute = data[0];
          staticRoutes.forEach((item) => {
            side.children.push(item);
          });   
          data[0] = side;  
            store.dispatch({
            type: 'update-routes',
            payload: { systemRoutes: data},
          });
        }else{
          store.dispatch({
            type: 'update-routes',
            payload: { systemRoutes: staticRoutes},
          });
        }
      })
      //初始化全局字典数据
      getDictDataMap().then((res)=>{
        const {success,data} = res.data
        if(success){
          setDictDataMap(JSON.stringify(data))
        }
      })

  }, []);

  useEffect(() => {
    changeTheme(theme);
  }, [theme]);

  const contextValue = {
    lang,
    setLang,
    theme,
    setTheme,
  };

  return (
    <BrowserRouter>
      <ConfigProvider
        locale={getArcoLocale()}
        componentConfig={{
          Card: {
            bordered: false,
          },
          List: {
            bordered: false,
          },
          Table: {
            border: false,
          },
        }}
      >
        <Provider store={store}>
          <GlobalContext.Provider value={contextValue}>
            <Switch>
              <Route path="/login" component={Login} />
              <Route path="/xterm" component={XtermPage} />
              <Route path="/" component={PageLayout} />
            </Switch>
          </GlobalContext.Provider>
        </Provider>
      </ConfigProvider>
    </BrowserRouter>
  );
}

ReactDOM.render(<Index />, document.getElementById('root'));
