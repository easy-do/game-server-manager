import { Button, ButtonGroup, Col, Empty, Row, Spin, Table } from "@douyinfe/semi-ui";

import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";
import {
    IllustrationNoContent,
    IllustrationConstructionDark,
  } from "@douyinfe/semi-illustrations";

const UserPoints = (props:any) => {
        const { userId } = props;
        const { OauthStore, UserPointsStore } = useStores();
        const { loginFlag } = OauthStore;
        const {
          currentPage,
          pageSize,
          total,
          pageRequest,
          onPageChange,
          onPageSizeChange,
          loadingData,
          dataList,
        } = UserPointsStore;

  useEffect(() => {
    pageRequest(userId);
  }, []);


  const columns = [
    {
      title: "原因",
      dataIndex: "description",
    },
    {
      title: "操作",
      render: (text: string, record: any, index: number) => {
        return record.currentPoints - record.points
      },
    },
    {
      title: "剩余积分",
      dataIndex: "currentPoints",
    },
    {
      title: "时间",
      dataIndex: "createTime",
    }
  ];

  return (
    <>

<Spin tip={"加载中...."} spinning={loadingData}>
      <Empty
        style={{ display: !loginFlag ? "block" : "none" }}
        image={<IllustrationNoContent style={{ width: 150, height: 150 }} />}
        darkModeImage={
          <IllustrationConstructionDark style={{ width: 150, height: 150 }} />
        }
        title={"未登录。"}
        description="点击头像登录,开始体验功能。"
      />
      {loginFlag ? (
        <Table
          title={
            <ButtonGroup theme="borderless">
              <Button onClick={() => pageRequest(userId)}>刷新</Button>
            </ButtonGroup>
          }
          columns={columns}
          dataSource={dataList}
          pagination={{
            style: {width:'100%',flexBasis: '100%', justifyContent:'center'},
            size: 'small',
            currentPage: currentPage,
            pageSize: pageSize,
            total: total,
            showSizeChanger: false,
            onPageChange: onPageChange,
            onPageSizeChange: onPageSizeChange,
          }}
        />
      ) 
      
      : (
        ""
      )}
    </Spin>

    </>
  );
};

export default observer(UserPoints);
