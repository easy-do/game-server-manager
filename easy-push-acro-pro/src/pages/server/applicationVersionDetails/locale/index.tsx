const i18n = {
  'en-US': {
    'list': 'List',
    'list.searchTable': 'Search Table',
    'searchTable.info.title': 'Details',
    'searchTable.update.title': 'Update',
    'searchTable.form.search': 'Search',
    'searchTable.form.reset': 'Reset',
    'searchTable.columns.id': 'id',
    'searchTable.columns.applicationId': 'applicationId',
    'searchTable.columns.applicationName': 'applicationName',
    'searchTable.columns.version': 'version',
    'searchTable.columns.status': 'status',
    'searchTable.columns.description': 'description',
    'searchTable.columns.heat': 'heat',
    'searchTable.columns.confData': 'confData',
    'searchTable.columns.createTime': 'createTime',
    'searchTable.columns.updateTime': 'updateTime',
    'searchTable.columns.createBy': 'createBy',
    'searchTable.columns.updateBy': 'updateBy',
    'searchTable.columns.delFlag': 'delFlag',
    'searchTable.columns.operations': 'Operation',
    'searchTable.columns.operations.view': 'View',
    'searchTable.columns.operations.update': 'Edit',
    'searchTable.columns.operations.remove': 'Remove',
    'searchTable.columns.operations.remove.confirm': 'Are you sure you want to delete?',
    'searchTable.columns.operations.install': 'Install',
    'searchTable.operations.add': 'New',
    'searchForm.all.placeholder': 'all',
    'searchForm.id.placeholder': 'Please enter the id',
    'searchForm.applicationId.placeholder': 'Please enter the applicationId',
    'searchForm.applicationName.placeholder': 'Please enter the applicationName',
    'searchForm.version.placeholder': 'Please enter the version',
    'searchForm.status.placeholder': 'Please enter the status',
    'searchForm.description.placeholder': 'Please enter the description',
    'searchForm.heat.placeholder': 'Please enter the heat',
    'searchForm.confData.placeholder': 'Please enter the confData',
    'searchForm.createTime.placeholder': 'Please enter the createTime',
    'searchForm.updateTime.placeholder': 'Please enter the updateTime',
    'searchForm.createBy.placeholder': 'Please enter the createBy',
    'searchForm.updateBy.placeholder': 'Please enter the updateBy',
    'searchForm.delFlag.placeholder': 'Please enter the delFlag',
    'searchTable.rules.id.required': 'id Can not be empty',
    'searchTable.rules.applicationId.required': 'applicationId Can not be empty',
    'searchTable.rules.applicationName.required': 'applicationName Can not be empty',
    'searchTable.rules.version.required': 'version Can not be empty',
    'searchTable.rules.status.required': 'status Can not be empty',
    'searchTable.rules.description.required': 'description Can not be empty',
    'searchTable.rules.heat.required': 'heat Can not be empty',
    'searchTable.rules.confData.required': 'confData Can not be empty',
    'searchTable.rules.createTime.required': 'createTime Can not be empty',
    'searchTable.rules.updateTime.required': 'updateTime Can not be empty',
    'searchTable.rules.createBy.required': 'createBy Can not be empty',
    'searchTable.rules.updateBy.required': 'updateBy Can not be empty',
    'searchTable.rules.delFlag.required': 'delFlag Can not be empty',
    'searchTable.rules.image.required': 'Image Can not be empty',
  
    'searchTable.columns.basicInfo': 'Basic info',
    'searchTable.columns.EnvInfo': 'Env info',
    'searchTable.columns.image': 'Image',
    'searchTable.columns.envName': 'Env name',
    'searchTable.columns.envKey': 'Env key',
    'searchTable.columns.envValue': 'Env value',
    'searchTable.columns.envDescription': 'Env description',
    'searchTable.columns.envType': 'Env type',
    'searchTable.columns.addEnv': 'Add env',
    'searchTable.rules.envName.required': 'Env name Can not be empty',
    'searchTable.rules.envKey.required': 'Env key Can not be empty',
    'searchTable.rules.envValue.required': 'Env value Can not be empty',
    'searchTable.rules.envDescription.required': 'Env description Can not be empty',
    'searchTable.rules.envType.required': 'Env type Can not be empty',
    'searchForm.image.placeholder': 'Please enter the docker image',
    'searchTable.columns.operations.audit': 'Audit',
    'searchTable.columns.operations.submitAudit': 'Submit Audit',
    'searchTable.columns.subApp': 'sub application',
    'searchTable.columns.addSubApp': 'add sub application',
    'searchTable.columns.name': 'name',
    'searchTable.columns.port': 'port',
    'searchTable.columns.containerPort': 'container Port',
    'searchTable.columns.localPort': 'local Port',
    'searchTable.columns.protocol': 'protocol',
    'searchTable.columns.networkMode': 'network Mode',
    'searchTable.columns.privileged': 'privileged',
    'searchTable.columns.addPort': 'add Port',
    'searchTable.columns.attachStdin': 'attachStdin',
    'searchTable.columns.stdinOpen': 'stdinOpen',
    'searchTable.columns.tty': 'tty',    
    'searchTable.columns.cmd': 'cmd',
    'searchTable.columns.labels': 'labels',  
    'searchForm.labels.placeholder': 'xxx=xxx,xxx=xxx', 
    'searchForm.cmd.placeholder': 'cmd1,cmd2', 
    'searchForm.columns.order': 'order',     
    'searchTable.columns.binds': 'bind Path',
    'searchTable.columns.addBinds': 'add Path Bind',
    'searchTable.columns.containerPath': 'container Path',
    'searchTable.columns.localPath': 'local Path',
    'searchTable.columns.editable': 'Editable',
    'searchTable.columns.publishAllPorts': 'publish All Ports',
    'searchTable.columns.nanoCPUs': 'CPU',
    'searchTable.columns.memory': 'memory(byte)',
    'searchTable.columns.shmSize': 'shm Size(byte)',
    'searchTable.columns.memorySwap': 'memory Swap(byte)',
    'searchTable.columns.restartPolicy': 'restartPolicy',  
    'searchTable.columns.networks': 'networks', 
    'searchTable.columns.createNetworks': 'create Networks',
    'searchTable.columns.addNetwork': 'add Networks',
    'searchTable.columns.networkName': 'network Name',
    'searchTable.columns.subNet': 'subNet',
    'searchTable.columns.gateway': 'gateway',
    'searchTable.columns.links': 'links',
    'searchForm.links.placeholder': 'containerName:alis,containerName1:alis1', 
    'jumpLogContent': 'Do you want to jump to the log page?', 
    
    
  },
  'zh-CN': {
    'list': '列表页',
    'list.searchTable': '条件搜索',
    'searchTable.info.title': '详情',
    'searchTable.update.title': '编辑',
    'searchTable.form.search': '查询',
    'searchTable.form.reset': '重置',
    'searchTable.columns.id': '编号',
    'searchTable.columns.applicationId': '应用id',
    'searchTable.columns.applicationName': '应用名称',
    'searchTable.columns.version': '版本',
    'searchTable.columns.status': '状态',
    'searchTable.columns.description': '详情',
    'searchTable.columns.heat': '热度',
    'searchTable.columns.confData': '配置信息',
    'searchTable.columns.createTime': '创建时间',
    'searchTable.columns.updateTime': '更新时间',
    'searchTable.columns.createBy': '创建人',
    'searchTable.columns.updateBy': '更新人',
    'searchTable.columns.delFlag': '删除标记',
    'searchTable.columns.operations': '操作',
    'searchTable.columns.operations.view': '查看',
    'searchTable.columns.operations.update': '修改',
    'searchTable.columns.operations.remove': '删除',
    'searchTable.columns.operations.remove.confirm': '确定要删除吗?', 
    'searchTable.columns.operations.install': '安装',
    'searchTable.operations.add': '新建',
    'searchForm.all.placeholder': '全部',
    'searchForm.id.placeholder': '请输入编号',
    'searchForm.applicationId.placeholder': '请输入应用id',
    'searchForm.applicationName.placeholder': '请输入应用名称',
    'searchForm.version.placeholder': '请输入版本',
    'searchForm.status.placeholder': '请输入状态',
    'searchForm.description.placeholder': '请输入详情',
    'searchForm.heat.placeholder': '请输入热度',
    'searchForm.confData.placeholder': '请输入配置信息',
    'searchForm.createTime.placeholder': '请输入创建时间',
    'searchForm.updateTime.placeholder': '请输入更新时间',
    'searchForm.createBy.placeholder': '请输入创建人',
    'searchForm.updateBy.placeholder': '请输入更新人',
    'searchForm.delFlag.placeholder': '请输入删除标记',
    'searchTable.rules.id.required': '编号不能为空',
    'searchTable.rules.applicationId.required': '应用id不能为空',
    'searchTable.rules.applicationName.required': '应用名称不能为空',
    'searchTable.rules.version.required': '版本不能为空',
    'searchTable.rules.status.required': '状态不能为空',
    'searchTable.rules.description.required': '详情不能为空',
    'searchTable.rules.image.required': '镜像不能为空',
    'searchTable.rules.heat.required': '热度不能为空',
    'searchTable.rules.confData.required': '配置信息不能为空',
    'searchTable.rules.createTime.required': '创建时间不能为空',
    'searchTable.rules.updateTime.required': '更新时间不能为空',
    'searchTable.rules.createBy.required': '创建人不能为空',
    'searchTable.rules.updateBy.required': '更新人不能为空',
    'searchTable.rules.delFlag.required': '删除标记不能为空',
    'searchTable.columns.basicInfo': '基本信息',
    'searchTable.columns.EnvInfo': '环境变量',
    'searchTable.columns.image': '镜像',
    'searchTable.columns.envName': '变量名',
    'searchTable.columns.envKey': 'key',
    'searchTable.columns.envValue': '默认值',
    'searchTable.columns.envDescription': '说明',
    'searchTable.columns.envType': '变量类型',
    'searchTable.columns.addEnv': '添加变量',
    'searchTable.rules.envName.required': '变量名不能为空',
    'searchTable.rules.envKey.required': '变量key不能为空',
    'searchTable.rules.envValue.required': '变量默认值不能为空',
    'searchTable.rules.envDescription.required': '备注不能为空',
    'searchTable.rules.envType.required': '变量类型不能为空',
    'searchForm.image.placeholder': 'docker镜像',
    'searchTable.columns.operations.audit': '审核',
    'searchTable.columns.operations.submitAudit': '提交审核',
    'searchTable.columns.subApp': '子应用',
    'searchTable.columns.addSubApp': '添加子应用',
    'searchTable.columns.name': '名称',
    'searchTable.columns.port': '端口',
    'searchTable.columns.addPort': '添加端口',
    'searchTable.columns.containerPort': '容器端口',
    'searchTable.columns.localPort': '宿主机端口',
    'searchTable.columns.protocol': '网络协议',
    'searchTable.columns.networkMode': '网络模式',
    'searchTable.columns.privileged': '特权模式',
    'searchTable.columns.attachStdin': '标准输出',
    'searchTable.columns.stdinOpen': '标准输入',
    'searchTable.columns.tty': '开启终端',    
    'searchTable.columns.cmd': '命令',
    'searchTable.columns.labels': '标签',  
    'searchForm.labels.placeholder': 'xxx=xxx,xxx=xxx', 
    'searchTable.columns.links': '链接',
    'searchForm.links.placeholder': '容器名:别名,容器名1:别名1', 
    'searchForm.cmd.placeholder': 'test1.test2', 
    'searchForm.columns.order': '排序', 
    'searchTable.columns.binds': '路径绑定',
    'searchTable.columns.addBinds': '添加路径绑定',
    'searchTable.columns.containerPath': '容器路径',
    'searchTable.columns.localPath': '宿主机路径',
    'searchTable.columns.editable': '可编辑',
    'searchTable.columns.publishAllPorts': '暴露所有端口',
    'searchTable.columns.nanoCPUs': 'CPU',
    'searchTable.columns.memory': '内存(MB)',
    'searchTable.columns.shmSize': '共享内存(MB)',
    'searchTable.columns.memorySwap': '交换内存(MB)',
    'searchTable.columns.restartPolicy': '重启策略',   
    'searchTable.columns.networks': '网络', 
    'searchTable.columns.createNetworks': '创建网络',
    'searchTable.columns.addNetwork': '添加网络',
    'searchTable.columns.networkName': '网络名称',
    'searchTable.columns.subNet': '子网',
    'searchTable.columns.gateway': '网关',
    'jumpLogContent': '是否跳转至日志页面?', 
    
  },
};

export default i18n;
