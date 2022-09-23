import React, { useEffect, useState } from 'react';
import {
  Button,
  Avatar,
  Descriptions,
  Tag,
  Skeleton,
  Link,
} from '@arco-design/web-react';
import useLocale from '@/utils/useLocale';
import locale from './locale';
import styles from './style/header.module.less';

import moment from 'moment';

export default function Info({
  userInfo = {},
  loading,
}: {
  userInfo: any;
  loading: boolean;
}) {
  const t = useLocale(locale);


  const loadingNode = <Skeleton text={{ rows: 1 }} animation />;
  return (
    <div className={styles['info-wrapper']}>
        <Avatar
            size={100}
            className={styles['info-avatar']}
          >
            <img src={userInfo.avatarUrl} />
          </Avatar>
      <Descriptions
        className={styles['info-content']}
        column={2}
        colon="："
        labelStyle={{ textAlign: 'right' }}
        data={[
          {
            label: t['userSetting.label.accountId'],
            value: loading ? loadingNode : userInfo.id,
          },
          {
            label: t['userSetting.label.name'],
            value: loading ? loadingNode : userInfo.nickName,
          },
          {
            label: t['userSetting.label.authorization'],
            value: loading ? (
              loadingNode
            ) : (
              <span>
                {userInfo.authorization ? (
                  <Tag color="green" className={styles['verified-tag']}>
                    {t['userSetting.value.isAuthorization']}
                  </Tag>
                ) : (
                  <Tag color="red" className={styles['verified-tag']}>
                    {t['userSetting.value.notAuthorization']}
                  </Tag>
                )}
                {/* <Link role="button" className={styles['edit-btn']}>
                  {t['userSetting.btn.edit']}
                </Link> */}
              </span>
            ),
          },
          {
            label: t['userSetting.info.email'],
            value: loading ? (
              loadingNode
            ) : (
              <span>
                {userInfo.email}
              </span>
            ),
          },
          {
            label: t['userSetting.label.registrationTime'],
            value: loading ? loadingNode : userInfo.creatTime,
          },
          {
            label: t['userSetting.label.lastLoginTime'],
            value: loading ? loadingNode : moment(userInfo.lastLoginTime*1000).format("YYYY-MM-DD HH:mm:ss"),
          },
          {
            label: t['userSetting.label.loginIp'],
            value: loading ? loadingNode : userInfo.loginIp,
          },
        ]}
      ></Descriptions>
    </div>
  );
}
