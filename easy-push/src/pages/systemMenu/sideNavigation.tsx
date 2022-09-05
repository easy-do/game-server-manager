import { Nav } from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useCallback, useEffect } from "react";
import useStores from "../../utils/store";
import { buildMenu } from "../../utils/menuUtil";
import { useLocation, useNavigate } from 'react-router-dom'

const SideNavigation = () => {

  useEffect(() => {
      userMenuTreeRequest();
  }, []);

  const { pathname } = useLocation();

  const navigate = useNavigate()


  const push = useCallback(
    (event: MouseEvent, path: string) => {
      event.preventDefault();
      navigate(path);
    },
    [navigate]
  );

  const { MenuManagerStore } = useStores();
  const { userMenuTreeRequest, userMenuTree } = MenuManagerStore;

  console.info("菜单加载")

  return userMenuTree.length > 0 &&  userMenuTree[0].length > 0? (
    <Nav
      selectedKeys={[pathname]}
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
        return buildMenu(item, 0, push);
      })}
    </Nav>
  ) : null;
};

export default observer(SideNavigation);
