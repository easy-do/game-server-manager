import React, { useEffect, useState } from 'react';
import cs from 'classnames';
import {
  Avatar,
  Button,
  Card,
  Carousel,
  Descriptions,
  Skeleton,
} from '@arco-design/web-react';
import useLocale from '@/utils/useLocale';
import locale from '../appInfo/locale';
import styles from './style/index.module.less';
import { downloadPath } from '@/api/oss';

interface CardInfoProps {
  card: any;
  optionCallCack?:(card,type)=>void
  loading?: boolean;
}

function CardInfo(props: CardInfoProps) {
  const { card = {} } = props;
  const [loading, setLoading] = useState(props.loading);

  const t = useLocale(locale);

  useEffect(() => {
    setLoading(props.loading);
  }, [props.loading]);


  const getButtonGroup = () => {
      return (
        <>
          <Button type='outline'>start</Button>
        </>
      );
  };

  const getContent = () => {
    if (loading) {
      return (
        <Skeleton
          text={{ rows: 3}}
          animation
          className={styles['card-block-skeleton']}
        />
      );
    }
    return (
      <>
      <Carousel
        
      >
        {card.picture.split(',').map((src, index) => (
          <div key={index}>
            <img
              src={downloadPath + src}
              style={{ width: '100%' }} 
              />
          </div>
        ))}
      </Carousel>
      <Descriptions
          column={1}
          data={[
             {
              label: t['searchTable.columns.version'],
              value: card.version,
            },
            {
              label: t['searchTable.columns.author'],
              value: card.author,
            },
            {
              label: t['searchTable.columns.description'],
              value: card.description,
            },
            {
              label: t['searchTable.columns.heat'],
              value: card.heat,
            },
          ]} /></>
    );
  };

  const className = cs(styles['card-block'], styles[`service-card`]);

  return (
    <Card
      bordered={true}
      className={className}
      size="small"
      title={
        loading ? (
          <Skeleton
            animation
            text={{ rows: 1, width: ['100%'] }}
            style={{ width: '120px', height: '24px' }}
            className={styles['card-block-skeleton']}
          />
        ) : (
          <>
          <Avatar
            size={50}
            className={styles['info-avatar']}
          >
            <img src={ downloadPath + card.icon} />
          </Avatar>
          {card.appName}
          </>
        )
      }
    >
      <div >{getContent()}</div>
      <div className={styles.extra}>{getButtonGroup()}</div>
    </Card>
  );
}

export default CardInfo;
