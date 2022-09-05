import { Button, Nav, Tooltip } from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useCallback, useEffect } from "react";
import useStores from "../../utils/store";
import UserMessage from "../userMessage/userMessage";
import AVA from "../../pages/avatar/avatar";


import { iocnMap } from "../../utils/iconUtil";
import { useLocation, useNavigate } from "react-router-dom";
import { buildMenu } from "../../utils/menuUtil";

const TopNavigation = () => {

  const navigate = useNavigate()

  const { OauthStore, MenuManagerStore } = useStores();

  const { userMenuTreeRequest, userMenuTree } = MenuManagerStore;

  const { loginFlag } = OauthStore;

  console.log('TopNavigation加載')

  const push = useCallback(
    (event: MouseEvent, path: string) => {
      event.preventDefault();
      navigate(path);
    },
    [navigate]
  );

  const { pathname } = useLocation();

  useEffect(() => {
        //加载主题样式
        const themeMode = localStorage.getItem("theme-mode");
        if (themeMode) {
          const body = document.body;
          body.setAttribute("theme-mode", "dark");
        }
          userMenuTreeRequest();
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
    selectedKeys={[pathname]}
    toggleIconPosition={'left'}
    mode="horizontal"
    footer={
      <>
      
      {loginFlag ?<UserMessage/>:null}

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
     {userMenuTree.length > 0 ? userMenuTree[1].items.map((item: any) => {
        return buildMenu(item, 0, push);
      }):null}
  </Nav>
  );
};

export default observer(TopNavigation);
