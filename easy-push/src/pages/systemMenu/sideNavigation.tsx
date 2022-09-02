import { Nav } from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";
import { buildMenu } from "../../utils/menuUtil";

const SideNavigation = () => {
  useEffect(() => {
    if (loginFlag) {
      userMenuTreeRequest();
    }
  }, []);

  const { OauthStore } = useStores();
  const { loginFlag, userMenuTreeRequest, userMenuTree } = OauthStore;



  return loginFlag && userMenuTree.length > 0 ? (
    <Nav
      limitIndent={false}
      header={{
        logo: (
          <img src="https://push.easydo.plus/favicon.ico" />
        ),
        text: "简单推送",
      }}
      footer={{
        collapseButton: true,
      }}
    >
      {userMenuTree[0].items.map((item: any) => {
        return buildMenu(item, 0);
      })}
    </Nav>
  ) : null;
};

export default observer(SideNavigation);
