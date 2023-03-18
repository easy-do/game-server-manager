import React, { useEffect, useState } from 'react';
import cs from 'classnames';
import {
  Button,
  Card,
  Descriptions,
  Divider,
  Image,
  Skeleton,
  Space,
} from '@arco-design/web-react';
import useLocale from '@/utils/useLocale';
import locale from '../application/locale';
import styles from './style/index.module.less';

interface CardInfoProps {
  data: any;
  optionCallCack?: (card, type) => void;
  loading?: boolean;
  installApplication:(id) => void;
}

function CardInfo(props: CardInfoProps) {
  const { data = {} } = props;
  const [loading, setLoading] = useState(props.loading);

  const t = useLocale(locale);

  useEffect(() => {
    setLoading(props.loading);
  }, [props.loading]);

  const installApplicationButton = () =>{
    props.installApplication(data.id)
  }

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
            value: data.applicationName,
          },
          {
            label: t['searchTable.columns.version'],
            value: data.version,
          },
          {
            label: t['searchTable.columns.author'],
            value: data.author,
          },
          {
            label: t['searchTable.columns.description'],
            value: data.description,
          },
          {
            label: t['searchTable.columns.heat'],
            value: data.heat,
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
      extra={<Button type="outline" onClick={installApplicationButton}>安装</Button>}
    >
      <Space split={<Divider type="vertical" />}>
        <Image height={'120px'} width={'120px'} src={data.icon} />
        <div>{getContent()}</div>
      </Space>
    </Card>
  );
}

export default CardInfo;
