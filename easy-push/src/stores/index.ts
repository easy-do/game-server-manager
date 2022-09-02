import { configure } from 'mobx'
import OauthStore from './module/OauthStore'
import HomeStorre from './module/HomeStore'
import SettingStore from './module/SettingStore'
import ServerInfoStore from './module/ServerInfoStore'
import ApplicationStore from './module/ApplicationStore'
import AuthCodeStore from './module/AuthCodeStore'
import AppInfoStore from './module/AppInfoStore'
import MEditStore from './module/MEditStore'
import AppScriptStore from './module/AppScriptStore'
import TopDataStore from './module/TopDataStore'
import JobStore from './module/JobStore'
import CronStore from './module/CronStore'
import JobLogStore from './module/JobLogStore'
import SysLogStore from './module/SysLogStore'
import AppStoreStore from './module/AppStoreStore'
import ExecLogStore from './module/ExecLogStore'
import DiscussionStore from './module/DiscussionStore'
import CommentDetailsStore from './module/CommentDetailsStore'
import UserMessageStore from './module/UserMessageStore'
import CommentStore from './module/CommentStore'
import UserPointsStore from './module/UserPointsStore'
import UploadStore from './module/UploadStore'
import UserManagerStore from './module/UserManagerStore'
import RoleManagerStore from './module/RoleManagerStore'
import MenuManagerStore from './module/MenuManagerStore'
import DictTypeStore from './module/DictTypeStore'
import DictDataStore from './module/DictDataStore'
import AuditStore from './module/AuditStore'
import ClientInfoStore from './module/ClientInfoStore'

configure({
  enforceActions: 'always' // 严格模式
})

const stores = {
  OauthStore,
  HomeStorre,
  SettingStore,
  ServerInfoStore,
  ApplicationStore,
  AuthCodeStore,
  AppInfoStore,
  MEditStore,
  AppScriptStore,
  TopDataStore,
  JobStore,
  CronStore,
  JobLogStore,
  SysLogStore,
  AppStoreStore,
  ExecLogStore,
  DiscussionStore,
  CommentDetailsStore,
  UserMessageStore,
  CommentStore,
  UserPointsStore,
  UploadStore,
  UserManagerStore,
  RoleManagerStore,
  MenuManagerStore,
  DictTypeStore,
  DictDataStore,
  AuditStore,
  ClientInfoStore,
}

export default stores
