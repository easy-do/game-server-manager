import { Layout } from "@douyinfe/semi-ui";
import RouteStting from "./pages/routeSetting/routeSetting";
import { Provider } from "mobx-react";
import stores from "./stores";
import Sider from "@douyinfe/semi-ui/lib/es/layout/Sider";
import FooterData from "./pages/footerData/footerData";
import { useEffect } from "react";
import SideNavigation from "./pages/systemMenu/sideNavigation";
import TopNavigation from "./pages/systemMenu/topNavigation";

export default function App() {
  const { Header, Footer, Content } = Layout;
  useEffect(() => {
  }, []);

  console.info("app重新加载")

  return (
    
      <Layout
        style={{
          border: "1px solid var(--semi-color-border)",
          height: "100vh",
        }}
      >
        <Sider style={{ backgroundColor: "var(--semi-color-bg-1)" }}>
        <Provider {...stores}>
            <SideNavigation/>
            </Provider>
        </Sider>
        <Layout style={{}}>
          <Header style={{ backgroundColor: "var(--semi-color-bg-1)" }}>
          <Provider {...stores}>
            <TopNavigation/>
            </Provider>
          </Header>
          <Content
            style={{
              padding: "24px",
              backgroundColor: "var(--semi-color-bg-0)",
            }}
          >
            <Provider {...stores}>
            <RouteStting />
            </Provider>
            
          </Content>
          <Footer
            style={{
              // display: 'flex',
              justifyContent: "space-between",
              padding: "20px",
              color: "var(--semi-color-text-2)",
              backgroundColor: "rgba(var(--semi-grey-0), 1)",
            }}
          >
            <Provider {...stores}>
            <FooterData />
            </Provider>
            
          </Footer>
        </Layout>
      </Layout>
    
  );
}
