import { Button, Empty } from "@douyinfe/semi-ui";
import {
  IllustrationSuccess,
  IllustrationSuccessDark,
} from "@douyinfe/semi-illustrations";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const { OauthStore } = useStores();
  const {userInfo} = OauthStore
  const navigate = useNavigate();

  //页面渲染执行
  useEffect(() => {
    OauthStore.autoLoginBytoken();
  },[]);

  const toHome=()=>{
    navigate("/home");
    window.location.reload()
  }

  return (
    <Empty
      title={userInfo.id? "登录成功":"自动登录中......"}
      image={<IllustrationSuccess style={{ width: 150, height: 150 }} />}
      darkModeImage={
        <IllustrationSuccessDark style={{ width: 150, height: 150 }} />
      }
      layout="horizontal"
      style={{ width: 800, margin: "0 auto" }}
    >
       { userInfo.id? <Button type="primary" onClick={toHome} theme="solid" style={{ padding: '6px 24px' }}>
            返回首页
        </Button> :''}
    </Empty>
  );
};

export default observer(Login);
