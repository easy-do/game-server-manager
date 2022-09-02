import { Spin, Table } from "@douyinfe/semi-ui";
import { observer } from "mobx-react";



const DynamicTablePage = () => {

    const loadingData = false
    const columns = [{}]
    const dataList = [{}]
    const currentPage = 1
    const pageSize = 10
    const total = 0;
    const onPageChange = () =>{

    }
    const onPageSizeChange = () =>{
        
    }
    return (
        <Spin tip={"加载中...."} spinning={loadingData}>
            <Table
              title={''}
              columns={columns}
              dataSource={dataList}
              pagination={{
                currentPage: currentPage,
                pageSize: pageSize,
                total: total,
                showSizeChanger: true,
                pageSizeOpts: [10, 20, 30, 50, 100],
                onPageChange: onPageChange,
                onPageSizeChange: onPageSizeChange,
              }} />
        </Spin>
      );
};

export default observer(DynamicTablePage);