import React, { useContext } from 'react';
import {
  Tooltip,
  Input,
  Avatar,
  Select,
  Dropdown,
  Menu,
  Divider,
  Message,
  Button,
} from '@arco-design/web-react';
import {
  IconLanguage,
  IconNotification,
  IconSunFill,
  IconMoonFill,
  IconUser,
  IconSettings,
  IconPoweroff,
  IconLoading,
  IconImport,
} from '@arco-design/web-react/icon';
import { useSelector } from 'react-redux';
import { GlobalState } from '@/store';
import { GlobalContext } from '@/context';
import useLocale from '@/utils/useLocale';
import Logo from '@/assets/216x216.svg';
import MessageBox from '@/components/MessageBox';
import IconButton from './IconButton';
import Settings from '../Settings';
import styles from './style/index.module.less';
import defaultLocale from '@/locale';
import { logoutRequest } from '@/api/oauth';
import checkLogin from '@/utils/checkLogin';

function Navbar({ topMenu, show }: { topMenu; show: boolean }) {
  const t = useLocale();
  const { userInfo, userLoading } = useSelector((state: GlobalState) => state);

  const { setLang, lang, theme, setTheme } = useContext(GlobalContext);

  function logout() {
    logoutRequest().then((res) => {
      const { success } = res.data;
      if (success) {
        localStorage.clear();
        sessionStorage.clear();
        window.location.href = '/';
      }
    });
  }

  function onMenuItemClick(key) {
    if (key === 'login') {
      window.location.href = '/login';
    } else if (key === 'info') {
      window.location.href = '/user/info';
    } else if (key === 'logout') {
      logout();
    } else if (key === 'setting') {
      window.location.href = '/user/setting';
    } else {
      Message.info(`You clicked ${key}`);
    }
  }

  if (!show) {
    return (
      <div className={styles['fixed-settings']}>
        <Settings
          trigger={
            <Button icon={<IconSettings />} type="primary" size="large" />
          }
        />
      </div>
    );
  }

  const droplist = (
    <Menu onClickMenuItem={onMenuItemClick}>
      {!checkLogin() ? (
        <Menu.Item key="login">
          <IconImport className={styles['dropdown-icon']} />
          {t['navbar.login']}
        </Menu.Item>
      ) : (
        <>
          <Menu.Item key="info">
            <IconUser className={styles['dropdown-icon']} />
            {userInfo.nickName}
          </Menu.Item>
          <Menu.Item key="setting">
            <IconSettings className={styles['dropdown-icon']} />
            {t['menu.user.setting']}
          </Menu.Item>
          <Divider style={{ margin: '4px 0' }} />
          <Menu.Item key="logout">
            <IconPoweroff className={styles['dropdown-icon']} />
            {t['navbar.logout']}
          </Menu.Item>
        </>
      )}
    </Menu>
  );

  return (
    <div className={styles.navbar}>
      <div className={styles.left}>
        <div className={styles.logo}>
          <Logo />
          <div className={styles['logo-name']}>简单推送</div>
        </div>
        {topMenu()}
      </div>

      <ul className={styles.right}>
        <li>
          <Input.Search
            className={styles.round}
            placeholder={t['navbar.search.placeholder']}
          />
        </li>
        <li>
          <Select
            triggerElement={<IconButton icon={<IconLanguage />} />}
            options={[
              { label: '中文', value: 'zh-CN' },
              { label: 'English', value: 'en-US' },
            ]}
            value={lang}
            triggerProps={{
              autoAlignPopupWidth: false,
              autoAlignPopupMinWidth: true,
              position: 'br',
            }}
            trigger="hover"
            onChange={(value) => {
              setLang(value);
              const nextLang = defaultLocale[value];
              Message.info(`${nextLang['message.lang.tips']}${value}`);
            }}
          />
        </li>
        {checkLogin() && (
          <li>
            <MessageBox userInfo={userInfo}>
              <IconButton icon={<IconNotification />} />
            </MessageBox>
          </li>
        )}
        <li>
          <Tooltip
            content={
              theme === 'light'
                ? t['settings.navbar.theme.toDark']
                : t['settings.navbar.theme.toLight']
            }
          >
            <IconButton
              icon={theme !== 'dark' ? <IconMoonFill /> : <IconSunFill />}
              onClick={() => setTheme(theme === 'light' ? 'dark' : 'light')}
            />
          </Tooltip>
        </li>
        <Settings />
        {userInfo && (
          <li>
            <Dropdown droplist={droplist} position="br" disabled={userLoading}>
              <Avatar size={32} style={{ cursor: 'pointer' }}>
                {userLoading ? (
                  <IconLoading />
                ) : !checkLogin() ? (
                  <div>未登录</div>
                ) : (
                  <img alt="avatar" src={userInfo.avatarUrl} />
                )}
              </Avatar>
            </Dropdown>
          </li>
        )}
      </ul>
    </div>
  );
}

export default Navbar;
