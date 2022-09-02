
import { Descriptions} from "@douyinfe/semi-ui";
import Paragraph from "@douyinfe/semi-ui/lib/es/typography/paragraph";
const ApplicationInfo = (props: any) => {
  const {dataInfo} = props;


  let pluingsData:any = {}
  if(dataInfo.pluginsData){
    pluingsData = JSON.parse(dataInfo.pluginsData)
  }
  return (
    <Descriptions>
    <Descriptions.Item itemKey="应用id"><Paragraph copyable={{content:dataInfo.applicationId}} >{dataInfo.applicationId}</Paragraph></Descriptions.Item>
    <Descriptions.Item itemKey="应用名称">{dataInfo.applicationName}</Descriptions.Item>
    <Descriptions.Item itemKey="服务器">{dataInfo.serverName}</Descriptions.Item>
    <Descriptions.Item itemKey="APP类型">{dataInfo.appName}</Descriptions.Item>
    <Descriptions.Item itemKey="状态">{dataInfo.status}</Descriptions.Item>
    <Descriptions.Item itemKey="创建时间">{dataInfo.createTime}</Descriptions.Item>
    <Descriptions.Item itemKey="最后在线时间">{dataInfo.lastUpTime}</Descriptions.Item>
    <Descriptions.Item itemKey="版本">{pluingsData && pluingsData.version ? pluingsData.version:'未同步'}</Descriptions.Item>
    <Descriptions.Item itemKey="外网ip">{pluingsData && pluingsData.ip ? pluingsData.ip:'未同步'}</Descriptions.Item>
    <Descriptions.Item itemKey="操作系统">{pluingsData && pluingsData.systemInfo? pluingsData.systemInfo.osName:'未同步'}</Descriptions.Item>
    <Descriptions.Item itemKey="CPU">{pluingsData && pluingsData.systemInfo? pluingsData.systemInfo.cpuInfo:'未同步'}</Descriptions.Item>
    <Descriptions.Item itemKey="内存">{pluingsData && pluingsData.systemInfo? pluingsData.systemInfo.memory:'未同步'}</Descriptions.Item>
    {pluingsData.config?<Descriptions.Item itemKey="配置文件"><Paragraph copyable={{content:pluingsData.config}} >点击复制</Paragraph></Descriptions.Item>:null}
    </Descriptions>
  );
};

export default ApplicationInfo;

