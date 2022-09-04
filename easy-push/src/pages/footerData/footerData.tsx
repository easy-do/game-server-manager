import { Col, Row } from "@douyinfe/semi-ui";
import { IconBytedanceLogo, IconCloudStroked, IconUserStroked, IconBarChartVStroked, IconGridStroked } from '@douyinfe/semi-icons';
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";
const FooterData = () => {
  const { TopDataStore } = useStores();

  const { hometopDataRequest, homeTop } = TopDataStore;

  useEffect(() => {
    hometopDataRequest();
  }, []);

  console.log('FooterData加載')
  return (
    <>
      <Row type="flex" justify="center">
        <Col span={4}>
          <span
            style={{
              display: "flex",
              alignItems: "center",
            }}
          >
            <IconBytedanceLogo size="large" style={{ marginRight: "8px" }} />
            {/* <span>Copyright © 2019 ByteDance. All Rights Reserved. </span> */}
          </span>
        </Col>
        <Col span={5}>
          <span>
            <IconUserStroked /> 累计注册 {homeTop.userCount} 
          </span>
        </Col>
        <Col span={5}>
          <span>
            <IconCloudStroked /> 在线用户 {homeTop.onlineCount} 
          </span>
        </Col>
        <Col span={5}>
          <span>
            <IconGridStroked /> 注册应用 {homeTop.applicationCount} 
          </span>
        </Col>
        <Col span={5}>
          <span>
            <IconBarChartVStroked /> 累积部署 {homeTop.deployCount} 
          </span>
        </Col>
      </Row>
    </>
  );
};

export default observer(FooterData);
