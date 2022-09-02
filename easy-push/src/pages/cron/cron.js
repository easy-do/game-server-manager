import QnnReactCron from "qnn-react-cron";
import { observer } from "mobx-react";
import { Button } from '@douyinfe/semi-ui';
import "antd/dist/antd.min.css"
import useStores from "../../utils/store";

// 可使用 QnnReactCron.Provider 配置国际化语言
// 无需配置语言时，可不使用  QnnReactCron.Provider
// QnnReactCron.Provider 应该包裹于入口组件以实现全部路由下的组件内部语言都被自定义


const Cronc = (props) => {

    const { CronStore } = useStores();

    const { cronValue, setValue } = CronStore;

    const { formApi, field } = props

    let cronFns;
    // language 为可选参数， 具体配置如下
    const language = {

        // 面板标题
        paneTitle: {
            second: "秒",
            minute: "分",
            hour: "时",
            day: "日",
            month: "月",
            week: "周",
            year: "年",
        },

        // assign  指定
        assign: "指定",
        // Don't assign  不指定
        donTAssign: "不指定",

        // Every minute ...   每一秒钟、每一分钟
        everyTime: {
            second: "每一秒钟",
            minute: "每一分钟",
            hour: "每一小时",
            day: "每一日",
            month: "每一月",
            week: "每一周",
            year: "每年",
        },

        // from [a] to [b] [unit], executed once [unit]    a 到 b 每一个时间单位执行一次
        aTob: {
            second: (AInput, BInput) => (
                <span>
                    从{AInput}-{BInput}秒，每秒执行一次
                </span>
            ),
            minute: (AInput, BInput) => (
                <span>
                    从{AInput}-{BInput}分，每分钟执行一次
                </span>
            ),
            hour: (AInput, BInput) => (
                <span>
                    从{AInput}-{BInput}时，每小时执行一次
                </span>
            ),
            day: (AInput, BInput) => (
                <span>
                    从{AInput}-{BInput}日，每日执行一次
                </span>
            ),
            month: (AInput, BInput) => (
                <span>
                    从{AInput}-{BInput}月，每月执行一次
                </span>
            ),
            week: (AInput, BInput) => (
                <span>
                    从{AInput}-{BInput}，每星期执行一次
                </span>
            ),
            year: (AInput, BInput) => (
                <span>
                    从{AInput}-{BInput}年，每年执行一次
                </span>
            ),
        },

        // from [a] [unit] start, every [b] Execute once [unit]   从 a 开始, 每一个时间单位执行一次
        aStartTob: {
            second: (AInput, BInput) => (
                <span>
                    从{AInput}秒开始，每{BInput}秒执行一次
                </span>
            ),
            minute: (AInput, BInput) => (
                <span>
                    从{AInput}分开始，每{BInput}分执行一次
                </span>
            ),
            hour: (AInput, BInput) => (
                <span>
                    从{AInput}时开始，每{BInput}小时执行一次
                </span>
            ),
            day: (AInput, BInput) => (
                <span>
                    从{AInput}日开始，每{BInput}日执行一次
                </span>
            ),
            month: (AInput, BInput) => (
                <span>
                    从{AInput}月开始，每{BInput}月执行一次
                </span>
            ),

            // [n] in the NTH week of this month    本月第 n 周的 星期[n] 执行一次
            week: (AInput, BInput) => (
                <span>
                    本月第{AInput}周的{BInput}执行一次
                </span>
            ),

            // 本月的最后一个 星期[n] 执行一次
            week2: (AInput) => <span>月的最后一个{AInput}执行一次</span>,

            year: (AInput, BInput) => (
                <span>
                    从{AInput}年开始，每{BInput}年执行一次
                </span>
            ),
        }

    };
    return (
        <QnnReactCron.Provider value={{ language }}>
            <QnnReactCron
                value={cronValue}
                panesShow={{
                    second: false,
                    minute:false,
                    hour: true,
                    day: true,
                    month:true,
                    week:true,
                    year:true,
                }}
                onOk={(value) => {

                }}
                getCronFns={(_cronFns) => {
                    cronFns = _cronFns;
                }}
                footer={[
                    <Button
                        key="cencel"
                        style={{ marginRight: 10 }}
                        onClick={() => {
                            setValue(formApi, field, '0 0 0 1/1 * ? *');
                        }}
                    >
                        重置
                    </Button>,
                    <Button
                        key="getValue"
                        type="primary"
                        onClick={() => {
                            setValue(formApi, field, cronFns.getValue());
                        }}
                    >
                        生成
                    </Button>
                ]}
            />
        </QnnReactCron.Provider>

    )

}

export default observer(Cronc);