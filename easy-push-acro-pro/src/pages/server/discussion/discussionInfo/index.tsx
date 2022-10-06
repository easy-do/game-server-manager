import React, { useEffect, useState } from 'react';
import { Descriptions, Divider, Skeleton, Spin, Typography } from '@arco-design/web-react';
import locale from '../locale';
import useLocale from '@/utils/useLocale';
import { infoRequest } from '@/api/discussion';
import { DataInfoVo } from '../constants';
import MarkdownEditor from '@/components/MarkdownEditor/MarkdownEditor';
import CommentPage from '../commentPage';
const { Title } = Typography;

function DiscussionInfo(props) {

    const [loading,setLoading] = useState(false)

    const [infoData, setInfoData] = useState<DataInfoVo>();

    const params = props.match.params
 
    function fetchData() {
      setLoading(true)
      if (params.id !== undefined) {
        infoRequest(params.id).then((res) => {
          const { success, data } = res.data;
          if (success) {
            setInfoData(data);
          }
          setLoading(false)
        });
      }
    }

    useEffect(() => {
        fetchData();
      }, []);

  const t = useLocale(locale);

  const data = [
    {
      label: t['searchTable.columns.createName'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.createName:'',
    },
    {
      label: t['searchTable.columns.createTime'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.createTime:'',
    },
    {
      label: t['searchTable.columns.updateTime'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.updateTime:'',
    },
    // {
    //   label: t['searchTable.columns.hop'],
    //   value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.hop:'',
    // },
    // {
    //   label: t['searchTable.columns.agree'],
    //   value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.agree:'',
    // },
    // {
    //   label: t['searchTable.columns.oppose'],
    //   value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.oppose:'',
    // },
  ];

  return (
      <div style={{width: '98%',margin: 'auto'}}>
        <Divider></Divider>
      <Title style={{margin: '0 auto', textAlign:'center'}} >{infoData? infoData.title:''}</Title>
      <Divider></Divider>
      <Descriptions
        column={3}
        data={data}
        style={{ marginBottom: 20 }}
        labelStyle={{ paddingRight: 36 }}
      />
      <Divider>{t['discussion.info.content']}</Divider>
      {infoData? <MarkdownEditor value={infoData.content} previewOnly={true}/>:null}
      <Divider>{t['discussion.info.comment']}</Divider>
        <CommentPage id={params.id} visible={true}/>
        <Divider>-</Divider>
      </div>
  );
}

export default DiscussionInfo;
