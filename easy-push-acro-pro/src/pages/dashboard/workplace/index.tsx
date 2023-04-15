import React from 'react';
import { Grid, Space } from '@arco-design/web-react';
import Overview from './overview';
import Shortcuts from './shortcuts';
import Announcement from './announcement';
import Docs from './docs';
import styles from './style/index.module.less';


const { Row, Col } = Grid;

const gutter = 16;

function Workplace() {
  return (
    <div className={styles.wrapper}>
      <Space size={16} direction="vertical" className={styles.left}>
        <Overview />
        {/* <Row gutter={gutter}> */}
        <Announcement />
          {/* <Col span={12}>
            <PopularContents />
          </Col>
          <Col span={12}>
            <ContentPercentage />
          </Col> */}
        {/* </Row> */}
      </Space>
      <Space className={styles.right} size={16} direction="vertical">
        <Shortcuts />
        {/* <Carousel /> */}
        <Docs />
      </Space>
    </div>
  );
}

export default Workplace;
