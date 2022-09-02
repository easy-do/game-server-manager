import { observer } from "mobx-react";
import { IllustrationNoContent } from "@douyinfe/semi-illustrations";
import {
  Avatar,
  Card,
  Col,
  Empty,
  Pagination,
  Row,
  Space,
  Spin,
  Typography,
} from "@douyinfe/semi-ui";
import useStores from "../../utils/store";
import { useEffect } from "react";

import { IllustrationNoContentDark } from "@douyinfe/semi-illustrations";


import { IconPulse } from "@douyinfe/semi-icons";

const AppStore = () => {
  useEffect(() => {
    pageRequest();
  }, []);

  const { AppStoreStore, UploadStore } = useStores();

  const { Meta } = Card;
  const { Text } = Typography;



  const {
    loadingData,
    currentPage,
    pageSize,
    total,
    onPageChange,
    pageRequest,
    dataList,
  } = AppStoreStore;

  const { dlAddress } = UploadStore;

  let rowList: any[] = [];
  dataList.map((row: any, i: number) => {
    let colList: any[] = [];
    // eslint-disable-next-line no-lone-blocks
    {
      row.map((col: any) => {
        colList.push(
          <Col
            span={4.8}
            // order={col.id}
            key={col.id}
          >
            <Card
              style={{ maxWidth: 340, maxHeight: 500 }}
              title={
                <Meta
                  title={col.appName}
                  avatar={
                    <Avatar
                      alt="Card meta img"
                      size="default"
                      src={col.icon? dlAddress+col.icon:"https://push.easydo.plus/logo192.png"}
                    />
                  }
                />
              }
              // headerExtraContent={
              //     <Text link>
              //         热度:{col.heat}
              //     </Text>
              // }
              cover={
                <img
                  height={216}
                  width={216}
                  alt="example"
                  src={col.picture?dlAddress+col.picture:"https://push.easydo.plus/logo192.png"}
                />
              }
              
              footerLine={true}
              footerStyle={{ display: "flex", justifyContent: "flex-end" }}
              footer={
                <Space>
                  {/* <Button theme='borderless' type='primary'>精选案例</Button> */}
                  <Text>作者:{col.author}</Text>
                  <Text style={{color:'red'}}><IconPulse />{col.heat}</Text>
                  {/* <Button theme="solid" type="primary">
                    立即开始
                  </Button> */}
                </Space>
              }
            >
            {/* <Meta 
                title={"作者:   " + col.author} 
                description={"热度:" + col.heat}
            /> */}
              <Text
                ellipsis={{ showTooltip: true }}
                style={{ width: "calc(400px - 76px)" }}
              >
                {col.description}
              </Text>
            </Card>
          </Col>
        );
      });
    }
    rowList.push(
      <Row type="flex" justify="space-around" key={i}>
        <Space>{colList}</Space>
      </Row>
    );
  });

  console.log(rowList);

  return (
    <Spin tip={"加载中...."} spinning={loadingData}>
      {dataList.length > 0 ? (
        <div>
          <div>{rowList}</div>

          {/* <Pagination
            style={{ display: "flex", justifyContent: "flex-end" }}
            total={total}
            currentPage={currentPage}
            pageSize={pageSize}
            showSizeChanger
            pageSizeOpts={[5, 10, 15, 20]}
            onPageChange={onPageChange}
            onPageSizeChange={onPageSizeChange}
          /> */}
          <Pagination size='small' style={{ width: '100%', flexBasis: '100%', justifyContent: 'center' }} pageSize={pageSize} total={total} currentPage={currentPage} onChange={cPage => onPageChange(cPage)} />
        </div>
      ) : (
        <Empty
          image={<IllustrationNoContent style={{ width: 150, height: 150 }} />}
          darkModeImage={
            <IllustrationNoContentDark style={{ width: 150, height: 150 }} />
          }
          description={"暂无内容"}
        />
      )}
    </Spin>
  );
};

export default observer(AppStore);
