
import {
    IconFolder,
    IconHome,
    IconTerminal,
    IconCloud,
    IconApps,
    IconAppCenter,
    IconComment,
    IconKey,
    IconClock,
    IconArticle,
    IconUserSetting,
    IconUser,
    IconUserCircle,
    IconSetting,
    IconMoon,
    IconShoppingBag,
    IconUserGroup,
    IconListView
} from "@douyinfe/semi-icons";
import { Form } from "@douyinfe/semi-ui";


export const iocnMap = new Map([
    ['IconFolder', <IconFolder />],
    ['IconHome', <IconHome />],
    ['IconTerminal', <IconTerminal />],
    ['IconCloud', <IconCloud />],
    ['IconApps', <IconApps />],
    ['IconAppCenter', <IconAppCenter />],
    ['IconComment', <IconComment />],
    ['IconKey', <IconKey />],
    ['IconClock', <IconClock />],
    ['IconArticle', <IconArticle />],
    ['IconUserSetting', <IconUserSetting />],
    ['IconUser', <IconUser />],
    ['IconUserCircle', <IconUserCircle />] ,
    ['IconMoon', <IconMoon />],
    ['IconShoppingBag', <IconShoppingBag />],
    ['IconSetting', <IconSetting />],
    ['IconUserGroup', <IconUserGroup />],
    ['IconListView', <IconListView />]
]);


const getIconKeyList = () => {
    let list = []
    iocnMap.forEach((key,value)=>{
        list.push(<Form.Select.Option key={value} lable={iocnMap.get(key)} value={value} />)
    })
    return list;
}

export const iconKeyList = getIconKeyList()
