import defaultSettings from '../settings.json';
export interface GlobalState {
  settings?: typeof defaultSettings;
  userInfo?: {
    id?: number;
    nickName?: string;
    unionId?: string;
    secret?: string;
    points?: number;
    authorization?: string;
    platform?: string;
    email?: string;
    avatarUrl?: string;
    loginIp?: string;
    lastLoginTime?: string;
    roles?: string[];
    resourceAction?: Record<string, string[]>;
    permissions?: string[];
  };
  userLoading?: boolean;
}

const initialState: GlobalState = {
  settings: defaultSettings,
  userInfo: {
    resourceAction: {},
  },
};

export default function store(state = initialState, action) {
  switch (action.type) {
    case 'update-settings': {
      const { settings } = action.payload;
      return {
        ...state,
        settings,
      };
    }
    case 'update-userInfo': {
      const { userInfo = initialState.userInfo, userLoading } = action.payload;
      return {
        ...state,
        userLoading,
        userInfo,
      };
    }
    default:
      return state;
  }
}
