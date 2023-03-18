import React, { useEffect, useState } from 'react';
import cs from 'classnames';
import {
  Button,
  Card,
  Descriptions,
  Notification,
  Divider,
  Image,
  Skeleton,
  Space,
} from '@arco-design/web-react';
import useLocale from '@/utils/useLocale';
import locale from '../application/locale';
import styles from './style/index.module.less';

interface CardInfoProps {
  card: any;
  optionCallCack?: (card, type) => void;
  loading?: boolean;
}

function CardInfo(props: CardInfoProps) {
  const { card = {} } = props;
  const [loading, setLoading] = useState(props.loading);

  const t = useLocale(locale);

  useEffect(() => {
    setLoading(props.loading);
  }, [props.loading]);

  const getContent = () => {
    if (loading) {
      return (
        <Skeleton
          text={{ rows: 5 }}
          animation
          className={styles['card-block-skeleton']}
        />
      );
    }
    return (
      <Descriptions
        column={1}
        data={[
          {
            label: t['searchTable.columns.applicationName'],
            value: card.applicationName,
          },
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
        ]}
      />
    );
  };

  const className = cs(styles['card-block'], styles[`service-card`]);

  return (
    <Card
      bordered={true}
      className={className}
      size="small"
      extra={<Button type="outline" onClick={()=>{        Notification.success({
        closable: false,
        title: '',
        content: '保持期待哦',
      })}}>安装</Button>}
    >
      <Space split={<Divider type="vertical" />}>
        <Image height={'120px'} width={'120px'} src={card.icon} />
        <div>{getContent()}</div>
      </Space>
    </Card>
  );
}

export default CardInfo;
