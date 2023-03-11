import React, { useEffect, useState } from 'react';
import cs from 'classnames';
import {
  Button,
  Card,
  Descriptions,
  Skeleton,
  Popconfirm,
} from '@arco-design/web-react';
import PermissionWrapper from '@/components/PermissionWrapper';
import useLocale from '@/utils/useLocale';
import locale from './locale';
import styles from './style/index.module.less';

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
          <Button
            type="text"
            size="small"
            onClick={() => props.optionCallCack(card, 'shell')}
          >
            {'命令行'}
          </Button>
        <PermissionWrapper
          requiredPermissions={[
            { resource: 'serverInfo', actions: ['serverInfo:info'] },
          ]}
        >
          <Button
            type="text"
            size="small"
            onClick={() => props.optionCallCack(card, 'view')}
          >
            {t['searchTable.columns.operations.view']}
          </Button>
        </PermissionWrapper><PermissionWrapper
          requiredPermissions={[
            { resource: 'serverInfo', actions: ['serverInfo:update'] },
          ]}
        >
            <Button
              type="text"
              size="small"
              onClick={() => props.optionCallCack(card, 'update')}
            >
              {t['searchTable.columns.operations.update']}
            </Button>
          </PermissionWrapper><PermissionWrapper
            requiredPermissions={[
              { resource: 'serverInfo', actions: ['serverInfo:remove'] },
            ]}
          >
            <Popconfirm
              title={t['searchTable.columns.operations.remove.confirm']}
              onOk={() => props.optionCallCack(card, 'remove')}
            >
              <Button type="text" status="warning" size="small">
                {t['searchTable.columns.operations.remove']}
              </Button>
            </Popconfirm>
          </PermissionWrapper></>
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
      <Descriptions
        column={1}
        data={[
          { label: t['searchTable.columns.serverName'], value: card.serverName },
          { label: t['searchTable.columns.userName'], value: card.userName },
          { label: t['searchTable.columns.createTime'], value: card.createTime },
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

          </>
        )
      }
    >
      <div className={styles.content}>{getContent()}</div>
      <div className={styles.extra}>{getButtonGroup()}</div>
    </Card>
  );
}

export default CardInfo;
