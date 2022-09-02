import { makeAutoObservable, runInAction } from "mobx";

class MEditStore {
  constructor() {
    makeAutoObservable(this);
  }

  value = "";

  language = "shell";

  width = "100%";

  height = "100%";

  theme = "vs-dark";

  options = {
    selectOnLineNumbers: true,
  };

  //在编辑器挂载之前发出的事件
  editorWillMount = (monaco: any) => {};

  //安装编辑器时发出的事件
  editorDidMount = (editor: any, monaco: any) => {
    editor.focus();
  };

  // 编辑器内容变化事件
  onChange = (newValue: any, e: any) => {
    runInAction(() => {
      this.value = newValue;
    });
  };

  //编辑器卸载之前发出的事件
  editorWillUnmount = (editor: any, monaco: any) => {};
}

export default new MEditStore();
