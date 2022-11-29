
export enum RoleEnum {
    SUPE_ADMIN = 'super_admin'
}


export enum SearchTypeEnum {
        EQ='EQ',
        NE='NE',
        IN='IN',
        NOT_IN='NOT_IN',
        LT='LT',
        GT='GT',
        LE='LE',
        GE='GE',
        BETWEEN='BETWEEN',
        NOT_BETWEEN='NOT_BETWEEN',
        LIKE='LIKE',
        NOT_LIKE='NOT_LIKE',
        LIKE_LEFT='LIKE_LEFT',
        LIKE_RIGHT='LIKE_RIGHT',
        NULL='null',
}

export const editLanguages = ['apex', 'azcli', 'bat', 'clojure', 'coffee', 'cpp', 'csharp', 'csp', 'css', 'dockerfile', 'fsharp', 'go', 'handlebars', 'html', 'ini', 'java', 'javascript', 'json', 'less', 'lua', 'markdown', 'msdax', 'mysql', 'objective', 'perl', 'pgsql', 'php', 'postiats', 'powerquery', 'powershell', 'pug', 'python', 'r', 'razor', 'redis', 'redshift', 'ruby', 'rust', 'sb', 'scheme', 'scss', 'shell', 'solidity', 'sql', 'st', 'swift', 'typescript', 'vb', 'xml', 'yaml']

export const socketAddress = "wss://push.easydo.plus/wss/browser";
// export const socketAddress = "ws://localhost:30002/wss/browser";
