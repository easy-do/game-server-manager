import react from "react";
import { Route, Routes } from "react-router-dom";
import Home from "../home/home";
import Seting from "../setting/setting";
import Login from "../login/login";
import Application from "../application/application";
import Server from "../server/server";
import AuthCode from "../authCode/authCode";
import AppInfo from "../appInfo/appInfo";
import AppScript from "../scriptData/scriptData";
import Job from "../job/job";
import JobLog from "../jobLog/jobLog";
import SysLog from "../sysLog/sysLog";
import AppStore from "../appStore/appStore";
import ExecLog from "../execLog/execLog";
import Discussion from "../discussion/discussion";
import DiscussionManager from "../discussion/discussionManager";
import FileStoreManager from "../fileStore/fileStoreManager";
import UserManager from "../userManager/userManager";
import RoleManager from "../roleManager/roleManager";
import MenuManager from "../menuManager/menuManager";
import { observer } from "mobx-react";
import DictType from "../dictMamager/dictType";
import ClientManager from "../clientInfo/clientManager";
import TemplateManager from "../template/templateManager";

class RouteStting extends react.Component<any, any> {
  constructor(props: any) {
    super(props);
  }

  render() {
    return (
      <Routes>
        <Route path="/" element={<Home />}></Route>
        <Route path="/home" element={<Home />}></Route>
        <Route path="/setting" element={<Seting />}></Route>
        <Route path="/server" element={<Server />}></Route>
        <Route path="/application" element={<Application />}></Route>
        <Route path="/authCode" element={<AuthCode />}></Route>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/app" element={<AppInfo />}></Route>
        <Route path="/scriptData" element={<AppScript />}></Route>
        <Route path="/job" element={<Job />}></Route>
        <Route path="/jobLog" element={<JobLog />}></Route>
        <Route path="/sysLog" element={<SysLog />}></Route>
        <Route path="/appStore" element={<AppStore />}></Route>
        <Route path="/execLog" element={<ExecLog />}></Route>
        <Route path="/discussion" element={<Discussion />}></Route>
        <Route path="/discussionManager" element={<DiscussionManager />}></Route>
        <Route path="/fileStoreManager" element={<FileStoreManager />}></Route>
        <Route path="/userManager" element={<UserManager />}></Route>
        <Route path="/roleManager" element={<RoleManager />}></Route>
        <Route path="/menuManager" element={<MenuManager />}></Route>
        <Route path="/dictType" element={<DictType />}></Route>
        <Route path="/client" element={<ClientManager />}></Route>
        <Route path="/template" element={<TemplateManager />}></Route>
      </Routes>
    );
  }
}

export default observer(RouteStting);
