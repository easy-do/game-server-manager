const i18n = {
  'en-US': {
    'list': 'List',
    'list.searchTable': 'Search Table',
    'searchTable.info.title': 'Details',
    'searchTable.update.title': 'Update',
    'searchTable.form.search': 'Search',
    'searchTable.form.reset': 'Reset',
    'searchTable.columns.id': 'id',
    'searchTable.columns.dockerModel': 'Model',
    'searchTable.columns.dockerName': 'dockerName',
    'searchTable.columns.dockerHost': 'dockerHost',
    'searchTable.columns.dockerCert': 'dockerCert',
    'searchTable.columns.dockerIsSsl': 'dockerIsSsl',
    'searchTable.columns.dockerCertPassword': 'dockerCertPassword',
    'searchTable.columns.createBy': 'createBy',
    'searchTable.columns.createTime': 'createTime',
    'searchTable.columns.updateBy': 'updateBy',
    'searchTable.columns.updateTime': 'updateTime',
    'searchTable.columns.operations': 'Operation',
    'searchTable.columns.operations.view': 'View',
    'searchTable.columns.imageList.view': 'Images',
    'searchTable.columns.containerList.view': 'Containers',
    'searchTable.columns.operations.update': 'Edit',
    'searchTable.columns.operations.remove': 'Remove',
    'searchTable.columns.operations.remove.confirm': 'Are you sure you want to delete?',
    'searchTable.operations.add': 'New',
    'searchTable.operations.pullImage': 'Pull Image',
    'searchForm.all.placeholder': 'all',
    'searchForm.id.placeholder': 'Please enter the id',
    'searchForm.dockerName.placeholder': 'Please enter the dockerName',
    'searchForm.dockerHost.placeholder': 'Please enter the dockerHost',
    'searchForm.dockerCert.placeholder': 'Please enter the dockerCert',
    'searchForm.dockerIsSsl.placeholder': 'Please enter the dockerIsSsl',
    'searchForm.dockerCertPassword.placeholder': 'Please enter the dockerCertPassword',
    'searchForm.createBy.placeholder': 'Please enter the createBy',
    'searchForm.createTime.placeholder': 'Please enter the createTime',
    'searchForm.updateBy.placeholder': 'Please enter the updateBy',
    'searchForm.updateTime.placeholder': 'Please enter the updateTime',
    'searchForm.dockerModel.placeholder': 'Please Select the model',
    'searchTable.rules.id.required': 'id Can not be empty',
    'searchTable.rules.dockerName.required': 'dockerName Can not be empty',
    'searchTable.rules.dockerHost.required': 'dockerHost Can not be empty',
    'searchTable.rules.dockerCert.required': 'dockerCert Can not be empty',
    'searchTable.rules.dockerIsSsl.required': 'dockerIsSsl Can not be empty',
    'searchTable.rules.dockerCertPassword.required': 'dockerCertPassword Can not be empty',
    'searchTable.rules.createBy.required': 'createBy Can not be empty',
    'searchTable.rules.createTime.required': 'createTime Can not be empty',
    'searchTable.rules.updateBy.required': 'updateBy Can not be empty',
    'searchTable.rules.updateTime.required': 'updateTime Can not be empty',
    'searchTable.columns.Version': 'Version',
    'searchTable.columns.Arch': 'Arch',
    'searchTable.columns.KernelVersion': 'KernelVersion',
    'searchTable.columns.GoVersion': 'GoVersion',
    'searchTable.columns.ApiVersion': 'ApiVersion',
    'searchTable.columns.MinAPIVersion': 'MinAPIVersion',
    'searchTable.columns.OperatingSystem': 'OperatingSystem',
    'searchTable.columns.Images': 'Images',
    'searchTable.columns.Containers': 'Containers',
    'searchTable.columns.ContainersRunning': 'ContainersRunning',
    'searchTable.columns.ContainersPaused': 'ContainersPaused',
    'searchTable.columns.ContainersStopped': 'ContainersStopped',
    'searchTable.columns.MemTotal': 'MemTotal',
    'searchTable.columns.Id': 'Id',
    'searchTable.columns.Repo': 'Repo',
    'searchTable.columns.Tag': 'Tag',
    'searchTable.columns.Digests': 'Digests',
    'searchTable.columns.Names': 'Names',
    'searchTable.columns.Image': 'Image',
    'searchTable.columns.NetworkMode': 'NetworkMode',
    'searchTable.columns.Status': 'Status',
    'searchTable.columns.operations.start': 'Start',
    'searchTable.columns.operations.restart': 'Restart',
    'searchTable.columns.operations.stop': 'Stop',
    'searchTable.columns.operations.rename': 'Rename',
    'searchTable.columns.operations.log': 'Log',
    'searchTable.columns.repository': 'Repository',
    'searchTable.columns.imageList.clientId': 'Client',
    'searchForm.clientId.placeholder': 'Please Select the Client',
    'searchTable.rules.clientId.required': 'ClientCan not be empty',
    'searchTable.columns.exited': 'exited',
    'searchTable.columns.running': 'running',
    'searchTable.columns.runTime': 'runTime',
    'searchTable.columns.created': 'created',
    
  },
  'zh-CN': {
    'list': '列表页',
    'list.searchTable': '条件搜索',
    'searchTable.info.title': '详情',
    'searchTable.update.title': '编辑',
    'searchTable.form.search': '查询',
    'searchTable.form.reset': '重置',
    'searchTable.columns.id': '主键',
    'searchTable.columns.dockerName': '名称',
    'searchTable.columns.dockerHost': 'ip地址',
    'searchTable.columns.dockerCert': '证书',
    'searchTable.columns.dockerIsSsl': '验证连接',
    'searchTable.columns.dockerCertPassword': '证书密码',
    'searchTable.columns.createBy': '创建人',
    'searchTable.columns.createTime': '创建时间',
    'searchTable.columns.updateBy': '更新人',
    'searchTable.columns.updateTime': '更新时间',
    'searchTable.columns.dockerModel': '模式',
    'searchTable.columns.operations': '操作',
    'searchTable.columns.operations.view': '查看',
    'searchTable.columns.imageList.view': '镜像列表',
    'searchTable.columns.containerList.view': '容器列表',
    'searchTable.columns.operations.update': '修改',
    'searchTable.columns.operations.remove': '删除',
    'searchTable.columns.operations.remove.confirm': '确定要删除吗?', 
    'searchTable.operations.add': '新建',
    'searchTable.operations.pullImage': '拉取镜像',
    'searchForm.all.placeholder': '全部',
    'searchForm.id.placeholder': '请输入主键',
    'searchForm.dockerName.placeholder': '请输入名称',
    'searchForm.dockerHost.placeholder': '请输入ip地址',
    'searchForm.dockerCert.placeholder': '请输入证书',
    'searchForm.dockerIsSsl.placeholder': '请输入验证连接',
    'searchForm.dockerCertPassword.placeholder': '请输入证书密码',
    'searchForm.createBy.placeholder': '请输入创建人',
    'searchForm.createTime.placeholder': '请输入创建时间',
    'searchForm.updateBy.placeholder': '请输入更新人',
    'searchForm.updateTime.placeholder': '请输入更新时间',
    'searchForm.dockerModel.placeholder': '请选择通信模式',
    'searchTable.rules.id.required': '主键不能为空',
    'searchTable.rules.dockerName.required': '名称不能为空',
    'searchTable.rules.dockerHost.required': 'ip地址不能为空',
    'searchTable.rules.dockerCert.required': '证书不能为空',
    'searchTable.rules.dockerIsSsl.required': '验证连接不能为空',
    'searchTable.rules.dockerCertPassword.required': '证书密码不能为空',
    'searchTable.rules.createBy.required': '创建人不能为空',
    'searchTable.rules.createTime.required': '创建时间不能为空',
    'searchTable.rules.updateBy.required': '更新人不能为空',
    'searchTable.rules.updateTime.required': '更新时间不能为空',
    'searchTable.columns.Version': 'Docker版本',
    'searchTable.columns.Arch': '平台',
    'searchTable.columns.KernelVersion': '内核版本',
    'searchTable.columns.GoVersion': 'Go语言版本',
    'searchTable.columns.ApiVersion': 'API版本',
    'searchTable.columns.MinAPIVersion': '最小API版本',
    'searchTable.columns.OperatingSystem': '操作系统',
    'searchTable.columns.Images': '镜像数量',
    'searchTable.columns.Containers': '容器数量',
    'searchTable.columns.ContainersRunning': '运行容器数量',
    'searchTable.columns.ContainersPaused': '暂停容器数量',
    'searchTable.columns.ContainersStopped': '停止容器数量',
    'searchTable.columns.MemTotal': '占用内存',
    'searchTable.columns.Id': 'id',
    'searchTable.columns.Repo': '仓库',
    'searchTable.columns.Tag': '标签',
    'searchTable.columns.Digests': '摘要',
    'searchTable.columns.Names': '名称',
    'searchTable.columns.Image': '镜像',
    'searchTable.columns.NetworkMode': '网络模式',
    'searchTable.columns.Status': '状态',
    'searchTable.columns.operations.start': '启动',
    'searchTable.columns.operations.restart': '重启',
    'searchTable.columns.operations.stop': '停止',
    'searchTable.columns.operations.rename': '重命名',
    'searchTable.columns.operations.log': '日志',
    'searchTable.columns.repository': '镜像仓库',
    'searchTable.columns.imageList.clientId': '客户端',
    'searchForm.clientId.placeholder': '请选择客户端',
    'searchTable.rules.clientId.required': '客户端必选',
    'searchTable.columns.exited': '停止',
    'searchTable.columns.running': '启动',
    'searchTable.columns.runTime': '运行时长',
    'searchTable.columns.created': '创建',
    
    
    
    
  },
};

export default i18n;

