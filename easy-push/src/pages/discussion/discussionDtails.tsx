import { Divider, Spin } from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import MarkdownEditor from "../markdownEditor/markdownEditor";
import useStores from "../../utils/store";
import { useEffect } from "react";
import Comment from "../comment/comment";

const DiscussionDtails = (props:any) => {
    const {discussionId} = props;

    const { DiscussionStore } = useStores();
    const {
      loadingData,
      dataInfo,
      getDiscussionDtails
    } = DiscussionStore;

  //页面渲染执行
  useEffect(() => {
    getDiscussionDtails(discussionId);
  }, [discussionId]);


  return (
    <Spin size="large" tip='加载中' spinning={loadingData}>
        <span>&nbsp;</span>
      <h2>{dataInfo.title}</h2>
      <Divider margin="12px" align="center">
        话题详情
      </Divider>
      <MarkdownEditor modelValue={dataInfo.content} previewOnly={true} />
      <Divider margin="12px" align="center">
        相关回复
      </Divider>
      <Comment discussionId={discussionId} type={1}/>
      <span>&nbsp;</span>
      <Divider margin="12px" align="center"></Divider>
      <span>&nbsp;</span>
    </Spin>
  );
};

export default observer(DiscussionDtails);
