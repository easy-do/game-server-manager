import { Nav } from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";
import { buildMenu } from "../../utils/menuUtil";
import { useNavigate } from 'react-router-dom'

const SideNavigation = () => {

  const navigate = useNavigate()

  const loginFlag = localStorage.getItem("loginFlag") ? true : false;

  useEffect(() => {
    if (loginFlag) {
      userMenuTreeRequest();
    }
  }, []);

  const { MenuManagerStore } = useStores();
  const { userMenuTreeRequest, userMenuTree } = MenuManagerStore;

  console.info("菜单加载")

  return loginFlag && userMenuTree.length > 0 ? (
    <Nav
      limitIndent={false}
      header={{
        logo: (
          <img src="/favicon.ico" />
        ),
        text: "简单推送",
      }}
      footer={{
        collapseButton: true,
      }}
    >
      {userMenuTree[0].items.map((item: any) => {
        return buildMenu(item, 0, navigate);
      })}
    </Nav>
  ) : null;
};

export default observer(SideNavigation);
