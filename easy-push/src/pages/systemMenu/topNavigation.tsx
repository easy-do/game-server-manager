import { Button, Nav, Tooltip } from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";
import UserMessage from "../userMessage/userMessage";
import AVA from "../../pages/avatar/avatar";


import { iocnMap } from "../../utils/iconUtil";
import { useNavigate } from "react-router-dom";

const TopNavigation = () => {

  const navigate = useNavigate()

  const { OauthStore, MenuManagerStore } = useStores();

  const { userMenuTreeRequest, userMenuTree } = MenuManagerStore;

  const { loginFlag, userInfo } = OauthStore;

  console.log('TopNavigation加載')

  useEffect(() => {
        //加载主题样式
        const themeMode = localStorage.getItem("theme-mode");
        if (themeMode) {
          const body = document.body;
          body.setAttribute("theme-mode", "dark");
        }
        if(loginFlag){
          userMenuTreeRequest();
        }
  }, []);

    //切换黑夜模式
    const switchMode = () => {
      const body = document.body;
      if (body.hasAttribute("theme-mode")) {
        body.removeAttribute("theme-mode");
        localStorage.removeItem("theme-mode");
      } else {
        localStorage.setItem("theme-mode", "dark");
        body.setAttribute("theme-mode", "dark");
      }
    };
  
  return (
    <Nav
    toggleIconPosition={'left'}
    mode="horizontal"
    footer={
      <>
      
      {userInfo.id ?<UserMessage/>:null}

        <Tooltip content={"暗色模式"}>
          <Button
            theme="borderless"
            icon={iocnMap.get('IconMoon')}
            onClick={switchMode}
            style={{
              color: "var(--semi-color-text-2)",
              marginRight: "12px",
            }}
          />
        </Tooltip>
        {/* 头像 */}
        <AVA />
      </>
    }
  >
    {/* { loginFlag && userMenuTree.length > 0 ? userMenuTree[1].items.map((item: any) => {
        return buildMenu(item, 0);
      }):null
    } */}


    <Nav.Item
      itemKey="appStore"
      text="APP市场"
      icon={iocnMap.get('IconShoppingBag')}
      onClick={()=>navigate("/appStore")}
      linkOptions={{onClick:e=>e.preventDefault()}}
    />
    <Nav.Item
      itemKey="appStore"
      text="讨论交流"
      icon={iocnMap.get('IconComment')}
      onClick={()=>navigate("/discussion")}
      // link={'/discussion'}
      linkOptions={{onClick:e=>e.preventDefault()}}
    />
    <Nav.Item
      itemKey="appStore"
      text="官方Q群"
      icon={iocnMap.get('IconUserGroup')}
      link={'https://jq.qq.com/?_wv=1027&k=OfQIQC2Y'}
    />
  </Nav>
  );
};

export default observer(TopNavigation);
