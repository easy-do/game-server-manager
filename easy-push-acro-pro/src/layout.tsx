import React, { useState, useRef, useMemo, useEffect } from 'react';
import { Switch, Route, Redirect, useHistory } from 'react-router-dom';
import { Layout, Menu, Breadcrumb, Spin } from '@arco-design/web-react';
import cs from 'classnames';
import {
  IconDashboard,
  IconList,
  IconSettings,
  IconFile,
  IconApps,
  IconCheckCircle,
  IconExclamationCircle,
  IconUser,
  IconMenuFold,
  IconMenuUnfold,
} from '@arco-design/web-react/icon';
import { useSelector } from 'react-redux';
import qs from 'query-string';
import NProgress from 'nprogress';
import Navbar from './components/NavBar';
import Footer from './components/Footer';
import { defaultRoute, IRoute } from '@/routes';
import useLocale from './utils/useLocale';
import getUrlParams from './utils/getUrlParams';
import lazyload from './utils/lazyload';
import { GlobalState } from './store';
import styles from './style/layout.module.less';

const MenuItem = Menu.Item;
const SubMenu = Menu.SubMenu;

const Sider = Layout.Sider;
const Content = Layout.Content;

function getIconFromKey(key) {
  switch (key) {
    case 'dashboard':
      return <IconDashboard className={styles.icon} />;
    case 'list':
      return <IconList className={styles.icon} />;
    case 'form':
      return <IconSettings className={styles.icon} />;
    case 'profile':
      return <IconFile className={styles.icon} />;
    case 'visualization':
      return <IconApps className={styles.icon} />;
    case 'result':
      return <IconCheckCircle className={styles.icon} />;
    case 'exception':
      return <IconExclamationCircle className={styles.icon} />;
    case 'user':
      return <IconUser className={styles.icon} />;
    default:
      return <div className={styles['icon-empty']} />;
  }
}

//加载扁平化路由
function getFlattenRoutes(routes) {
  const res = [];
  function travel(_routes) {
    _routes.forEach((route) => {
      const visibleChildren = (route.children || []).filter(
        (child) => child.status === 0
      );
      if (route.key && (!route.children || !visibleChildren.length)) {
        try {
          console.log(route.url)
          route.component = lazyload(() => import(`./pages/${route.url}`));
          res.push(route);
        } catch (e) {
          console.error(e);
        }
      }
      if (route.children && route.children.length) {
        travel(route.children);
      }
    });
  }
  travel(routes);
  return res;
}

function PageLayout() {
  const urlParams = getUrlParams();
  const history = useHistory();
  const pathname = history.location.pathname;
  const currentComponent = qs.parseUrl(pathname).url.slice(1);
  const locale = useLocale();
  const { settings, userLoading, systemRoutes } = useSelector(
    (state: GlobalState) => state
  );

  const defaultSelectedKeys = [currentComponent || defaultRoute];
  const paths = (currentComponent || defaultRoute).split('/');
  const defaultOpenKeys = paths.slice(0, paths.length - 1);

  const [breadcrumb, setBreadCrumb] = useState([]);
 
  const [collapsed, setCollapsed] = useState<boolean>(false);
  const [selectedKeys, setSelectedKeys] =
    useState<string[]>(defaultSelectedKeys);
  const [openKeys, setOpenKeys] = useState<string[]>(defaultOpenKeys);

  const routeMap = useRef<Map<string, React.ReactNode[]>>(new Map());
  const menuMap = useRef<
    Map<string, { menuItem?: boolean; subMenu?: boolean }>
  >(new Map());

  const navbarHeight = 60;
  const menuWidth = collapsed ? 48 : settings.menuWidth;

  const showNavbar = settings.navbar && urlParams.navbar !== false;
  const showMenu = settings.menu && urlParams.menu !== false;
  const showFooter = settings.footer && urlParams.footer !== false;


  // const flattenRoutes = getFlattenRoutes(systemRoutes);
  const flattenRoutes = useMemo(() => getFlattenRoutes(systemRoutes) || [], [systemRoutes]);

  function renderRoutes(locale) {
    routeMap.current.clear();
    return function travel(_routes: IRoute[], level, parentNode = []) {
      return _routes.map((route) => {
        const { breadcrumb = true, status } = route;
        //菜单开启才构建
        if(status === 0){
        //获取菜单图标
        const iconDom = getIconFromKey(route.icon);
        //菜单显示的文字
        const titleDom = (
          <>
            {iconDom} {locale[route.key] || route.name}
          </>
        );

        //设置面包屑
        routeMap.current.set(
          `/${route.key}`,
          breadcrumb ? [...parentNode, route.name] : []
        );

        //过滤出需要继续递归的菜单？
        const visibleChildren = (route.children || []).filter((child) => {
          const { breadcrumb = true, status} = child;
          if (status === 1) {
            routeMap.current.set(
              `/${child.key}`,
              breadcrumb ? [...parentNode, route.name, child.name] : []
            );
          }
          //菜单开启
          return status === 0;
        });

        //如果还有子菜单继续递归构建子菜单
        if (visibleChildren.length) {
          menuMap.current.set(route.key, { subMenu: true });
          return (
            <SubMenu key={route.key} title={titleDom}>
              {travel(visibleChildren, level + 1, [...parentNode, route.name])}
            </SubMenu>
          );
        }
        //到底了，直接返回子菜单组件
        menuMap.current.set(route.key, { menuItem: true });
        return <MenuItem key={route.key}>{titleDom}</MenuItem>;
        }
      });
    };
  }

  // 点击菜单的回调
  function onClickMenuItem(key) {

    const currentRoute = flattenRoutes.find((r) => r.key === key);

    //如果路径是网址应该跳转

    if(currentRoute.url.substring(0, 7) === "http://"|| currentRoute.url.substring(0, 8) === "https://"){
      window.open(currentRoute.url);
      return
    }

    
    const component = currentRoute.component;
    const preload = component.preload();
    NProgress.start();
    preload.then(() => {
      history.push(`/${currentRoute.url}`);
      NProgress.done();
    });
  }

  function toggleCollapse() {
    setCollapsed((collapsed) => !collapsed);
  }

  const paddingLeft = showMenu ? { paddingLeft: menuWidth } : {};
  const paddingTop = showNavbar ? { paddingTop: navbarHeight } : {};
  const paddingStyle = { ...paddingLeft, ...paddingTop };
  
  function updateMenuStatus() {
    const pathKeys = pathname.split('/');
    const newSelectedKeys: string[] = [];
    const newOpenKeys: string[] = [...openKeys];
    while (pathKeys.length > 0) {
      const currentRouteKey = pathKeys.join('/');
      const menuKey = currentRouteKey.replace(/^\//, '');
      const menuType = menuMap.current.get(menuKey);
      if (menuType && menuType.menuItem) {
        newSelectedKeys.push(menuKey);
      }
      if (menuType && menuType.subMenu && !openKeys.includes(menuKey)) {
        newOpenKeys.push(menuKey);
      }
      pathKeys.pop();
    }
    setSelectedKeys(newSelectedKeys);
    setOpenKeys(newOpenKeys);
  }

  useEffect(() => {
    const routeConfig = routeMap.current.get(pathname);
    //展示面包屑
    setBreadCrumb(routeConfig || []);
    updateMenuStatus();
  }, [pathname]);



  const topNav = () =>{
     return <Menu
      mode={'horizontal'}
      collapse={collapsed}
      onClickMenuItem={onClickMenuItem}
      selectedKeys={selectedKeys}
      openKeys={openKeys}
      onClickSubMenu={(_, openKeys) => setOpenKeys(openKeys)}
    >
      {renderRoutes(locale)(systemRoutes[1].children || [], 1)}
    </Menu>
  }

  return (
    <Layout className={styles.layout}>
      <div
        className={cs(styles['layout-navbar'], {
          [styles['layout-navbar-hidden']]: !showNavbar,
        })}
      >
        <Navbar topMenu={topNav} show={showNavbar} />
      </div>
      {userLoading ? (
        <Spin className={styles['spin']} />
      ) : (
        <Layout>
          {showMenu && (
            <Sider
              className={styles['layout-sider']}
              width={menuWidth}
              collapsed={collapsed}
              onCollapse={setCollapsed}
              trigger={null}
              collapsible
              breakpoint="xl"
              style={paddingTop}
            >
              <div className={styles['menu-wrapper']}>
                <Menu
                  collapse={collapsed}
                  onClickMenuItem={onClickMenuItem}
                  selectedKeys={selectedKeys}
                  openKeys={openKeys}
                  onClickSubMenu={(_, openKeys) => setOpenKeys(openKeys)}
                >
                  {renderRoutes(locale)(systemRoutes[0].children, 1)}
                </Menu>
              </div>
              <div className={styles['collapse-btn']} onClick={toggleCollapse}>
                {collapsed ? <IconMenuUnfold /> : <IconMenuFold />}
              </div>
            </Sider>
          )}
          <Layout className={styles['layout-content']} style={paddingStyle}>
            <div className={styles['layout-content-wrapper']}>
              {!!breadcrumb.length && (
                <div className={styles['layout-breadcrumb']}>
                  <Breadcrumb>
                    {breadcrumb.map((node, index) => (
                      <Breadcrumb.Item key={index}>
                        {typeof node === 'string' ? locale[node] || node : node}
                      </Breadcrumb.Item>
                    ))}
                  </Breadcrumb>
                </div>
              )}
              <Content>
                <Switch>
                  {flattenRoutes.map((route, index) => {
                    return (
                      <Route
                        key={index}
                        path={`/${route.url}`}
                        component={route.component}
                      />
                    );
                  })}
                  <Route exact path="/">
                    <Redirect to={`/${defaultRoute}`} />
                  </Route>
                  <Route
                    path="*"
                    component={lazyload(() => import('./pages/exception/403'))}
                  />
                </Switch>
              </Content>
            </div>
            {showFooter && <Footer />}
          </Layout>
        </Layout>
      )}
    </Layout>
  );
}

export default PageLayout;
