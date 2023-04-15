import React, { useState, useEffect } from 'react';
import { Avatar, Comment, PaginationProps } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { IconHeart, IconHeartFill, IconMessage } from '@arco-design/web-react/icon';
import { page } from '@/api/commentDetails';


export interface CommentPageProps{
  id:string;
  visible:boolean;

}

function CommentPage(props:CommentPageProps) {
  
    const [loading,setLoading] = useState(false)

    const [like, setLike] = useState(false);
    const [star, setStar] = useState(false);

    const [pagination, setPatination] = useState<PaginationProps>({
      sizeCanChange: true,
      showTotal: true,
      pageSize: 10,
      current: 1,
      pageSizeChangeResetCurrent: true,
    });

    const [infoData, setInfoData] = useState([]);

    function fetchData() {
      setLoading(true)
      if (props.id && props.visible) {
        const { current, pageSize } = pagination;
        page({
          discussionId:props.id,
          currentPage: current,
          pageSize,
          searchParam: {},
          orders: [],
          columns: [],
          searchConfig:{},
        }).then((res) => {
          const { success, data } = res.data;
          if (success) {
            setInfoData(data);
          }
          setLoading(false)
        });
      }
    }

    useEffect(()=>{
      fetchData()
    },[props.id])


    const actions = [
      <span className='custom-comment-action' key='heart' onClick={() => setLike(!like)}>
        {like ? (
          <IconHeartFill style={{ color: '#f53f3f' }}/>
        ) : (
          <IconHeart />
        )}
        {0}
      </span>,
      // <span className='custom-comment-action' key='star' onClick={() => setStar(!star)}>
      //   {star ? (
      //     <IconStarFill style={{ color: '#ffb400' }}/>
      //   ) : (
      //     <IconStar />
      //   )}
      //   {0}
      // </span>,
      <span className='custom-comment-action' key='reply'>
        <IconMessage /> Reply
      </span>,
    ];
    

  const t = useLocale(locale);

  const comments = []

  infoData.map((item)=>{
    comments.push(
    <Comment
          actions={actions}
          author={item.userName}
          avatar={
            <Avatar>
              <img
                alt='avatar'
                src={item.userAvatar}
              />
            </Avatar>
          }
        content={<div>{item.content}</div>}
        datetime={item.createTime}
      />
    )
  })


  return (
    <>
      {comments}
    </>
  );
}

export default CommentPage;
