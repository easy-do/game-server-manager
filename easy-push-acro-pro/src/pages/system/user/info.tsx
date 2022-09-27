import React, { useEffect, useState } from 'react';
import {
  Descriptions, Drawer, Typography,
} from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { infoRequest } from '@/api/userManager';
import { UserInfoVo } from './constants';

const { Text } = Typography;

function InfoPage(props: {id:number,visible,setVisible}) {

    const [infoData, setInfoData] = useState<UserInfoVo>();
    
    function fetchData (){
        if(props.id !== undefined && props.visible){
            infoRequest(props.id).then((res)=>{
                const { success, data} = res.data
                if(success){
                    setInfoData(data)
                }
            })
        }
    }

    useEffect(() => {
        fetchData();
      }, [props.id,props.visible]);

  const t = useLocale(locale);

  const authorizationInfo = infoData && infoData.authorization
  ? JSON.parse(infoData.authorization)
  : '';

  const data = [
    {
      label: t['searchTable.columns.id'],
      value: infoData? infoData.id:'',
    },
    {
        label: t['searchTable.columns.unionId'],
        value: infoData? infoData.unionId:'',
      },
    {
        label: t['searchTable.columns.name'],
        value: infoData? infoData.nickName:'',
    },

    {
        label: t['searchTable.columns.status'],
        value: infoData? infoData.state === 0 ? '正常':'禁用':'',
    },
    {
        label: t['searchTable.columns.points'],
        value: infoData? infoData.points:'',
    },
    {
        label: t['searchTable.columns.email'],
        value: infoData? infoData.email:'',
    },
    {
        label: t['searchTable.columns.secret'],
        value: infoData? <Text copyable>{infoData.secret}</Text>:'',
    },
    {
        label: t['searchTable.columns.platform'],
        value: infoData? infoData.platform:'',
    },
    {
        label: t['searchTable.columns.createTime'],
        value: infoData? infoData.createTime:'',
    },
    {
        label: t['searchTable.columns.lastLoginTime'],
        value: infoData? infoData.lastLoginTime:'',
    },
    {
        label: t['searchTable.columns.loginIp'],
        value: infoData? infoData.loginIp:'',
    },   
    {
        label: t['searchTable.columns.authorization'],
        value: infoData? authorizationInfo:'',
    },   
  ];

  return (
     <Drawer
        width={500}
        title={t['searchTable.info.title']}
        visible={props.visible}
        onOk={() => {
            props.setVisible(false);
        }}
        onCancel={() => {
            props.setVisible(false);
        }}
      >
    <Descriptions
      column={1}
      title={t['searchTable.info.subTitle']}
      data={data}
      style={{ marginBottom: 20 }}
      labelStyle={{ paddingRight: 36 }}
    />
      </Drawer>

  );
}

export default InfoPage;
