
import { Descriptions} from "@douyinfe/semi-ui";
import Paragraph from "@douyinfe/semi-ui/lib/es/typography/paragraph";
const ClientManagerInfo = (props: any) => {
  const {dataInfo} = props;

  let clientData:any = {}
  if(dataInfo.clientData){
    clientData = JSON.parse(dataInfo.clientData)
  }

  return (
    
    <Descriptions>
    <Descriptions.Item itemKey="客户端id"><Paragraph copyable={{content:dataInfo.id}} >{dataInfo.id}</Paragraph></Descriptions.Item>
    <Descriptions.Item itemKey="名称">{dataInfo.clientName}</Descriptions.Item>
    <Descriptions.Item itemKey="服务器">{dataInfo.serverName?dataInfo.serverName:'未绑定'}</Descriptions.Item>
    <Descriptions.Item itemKey="外网ip">{clientData && clientData.ip ? clientData.ip:'未同步'}</Descriptions.Item>
    <Descriptions.Item itemKey="状态">{dataInfo.status}</Descriptions.Item>
    <Descriptions.Item itemKey="创建时间">{dataInfo.createTime}</Descriptions.Item>
    <Descriptions.Item itemKey="最后在线时间">{dataInfo.lastUpTime}</Descriptions.Item>
    <Descriptions.Item itemKey="版本">{clientData && clientData.version ? clientData.version:'未同步'}</Descriptions.Item>
    <Descriptions.Item itemKey="操作系统">{clientData && clientData.systemInfo? clientData.systemInfo.osName:'未同步'}</Descriptions.Item>
    <Descriptions.Item itemKey="CPU">{clientData && clientData.systemInfo? clientData.systemInfo.cpuInfo:'未同步'}</Descriptions.Item>
    <Descriptions.Item itemKey="内存">{clientData && clientData.systemInfo? clientData.systemInfo.memory:'未同步'}</Descriptions.Item>
    {/* {clientData.config?<Descriptions.Item itemKey="配置文件"><Paragraph copyable={{content:clientData.config}} >点击复制</Paragraph></Descriptions.Item>:null} */}
    </Descriptions>
  );
};

export default ClientManagerInfo;

