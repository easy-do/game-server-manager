import { makeAutoObservable, runInAction } from "mobx";
import { hometopData } from "../../api/topData";

class TopDataStore {
  constructor() {
    makeAutoObservable(this);
  }

  homeTop = {}

  /**
   * 获取统计数据
   */
   hometopDataRequest  = () => {
    hometopData().then((result) => {
      if(result.data.success){
        runInAction(() => {
          this.homeTop = result.data.data;
        })
      }
    })
  }

  
}

export default new TopDataStore();
