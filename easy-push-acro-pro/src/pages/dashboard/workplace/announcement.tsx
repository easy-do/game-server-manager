import React, { useState, useEffect } from 'react';
import { Link, Card, Skeleton, Tag, Typography } from '@arco-design/web-react';
import useLocale from '@/utils/useLocale';
import locale from './locale';
import styles from './style/announcement.module.less';
import { getSearChColumns } from '@/pages/system/notice/constants';
import { managerPage } from '@/api/notice';
import InfoPage from '@/pages/system/notice/info';

function Announcement() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  const t = useLocale(locale);

  const fetchData = () => {
    setLoading(true);
    managerPage({
      searchParam: null,
      orders: null,
      columns: getSearChColumns(),
      searchConfig: null,
    }).then((res) => {
      setData(res.data.data);
      setLoading(false);
    });
  };

  useEffect(() => {
    fetchData();
  }, []);


    //查看
    const [viewInfoId, setViewInfoId] = useState();
    const [isViewInfo, setisViewInfo] = useState(false);
  
    function viewInfo(id) {
      setViewInfoId(id);
      setisViewInfo(true);
    }

  function getTagColor(type) {
    switch (type) {
      case '3':
        return 'orangered';
      case '1':
        return 'cyan';
      case '2':
        return 'arcoblue';
      default:
        return 'arcoblue';
    }
  }

  return (
    <Card>
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <Typography.Title heading={6}>
          {t['workplace.announcement']}
        </Typography.Title>
        <Link>{t['workplace.seeMore']}</Link>
      </div>
      <Skeleton loading={loading} text={{ rows: 5, width: '100%' }} animation>
        <div>
          {data.map((d) => (
            <div key={d.title} className={styles.item}>
              <Tag color={getTagColor(d.noticeType)} size="small">
                {t[`workplace.${d.noticeType}`]}
              </Tag>
              <span className={styles.link} onClick={()=>viewInfo(d.id)} >{d.noticeTitle}</span>
            </div>
          ))}
        </div>
      </Skeleton>
      <InfoPage
        id={viewInfoId}
        visible={isViewInfo}
        setVisible={setisViewInfo}
      />
    </Card>
  );
}

export default Announcement;
