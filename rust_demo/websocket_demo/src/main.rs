// let mut ws = WebSocket::connect("wss://push.easydo.plus/wss/client").await.unwrap();
// let (mut r, mut w) = ws.split();
//
// let send_str = "{\"clientId\":\"20230312180218-b3c0a4f4-0df6-4cf0-b2a4-5bf2a767ffca\",\"data\":\"{\\\"clientId\\\":\\\"20230312180218-b3c0a4f4-0df6-4cf0-b2a4-5bf2a767ffca\\\",\\\"env\\\":{\\\"PATH\\\":\\\"/usr/java/openjdk-18/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin\\\",\\\"TZ\\\":\\\"Asia/Shanghai\\\",\\\"JAVA_HOME\\\":\\\"/usr/java/openjdk-18\\\",\\\"TERM\\\":\\\"xterm\\\",\\\"JVM_OPTS\\\":\\\"=\\\",\\\"LANG\\\":\\\"C.UTF-8\\\",\\\"HOSTNAME\\\":\\\"d2d2f92f9eb2\\\",\\\"CLIENT_ID\\\":\\\"20230312180218-b3c0a4f4-0df6-4cf0-b2a4-5bf2a767ffca\\\",\\\"PARAMS\\\":\\\"=\\\",\\\"PWD\\\":\\\"/\\\",\\\"JAVA_VERSION\\\":\\\"18.0.1.1\\\",\\\"SHLVL\\\":\\\"1\\\",\\\"HOME\\\":\\\"/root\\\",\\\"_\\\":\\\"/usr/java/openjdk-18/bin/java\\\"},\\\"ip\\\":\\\"223.198.106.81\\\",\\\"port\\\":\\\"33717\\\",\\\"systemInfo\\\":{\\\"cpuInfo\\\":\\\"CPU核心数=4,CPU总的使用率=3990.0,CPU系统使用率=0.0,CPU用户使用率=0.0,CPU当前等待率=0.0,CPU当前空闲率=100.0,CPU利用率=0.0,CPU型号信息='CommonKVMprocessor\\\\n1physicalCPUpackage(s)\\\\n4physicalCPUcore(s)\\\\n4logicalCPU(s)\\\\nIdentifier:Intel64Family15Model6Stepping1\\\\nProcessorID:178BFBFF00000F61\\\\nMicroarchitecture:Netburst\\\",\\\"memory\\\":\\\"Available:14.2GiB/15.5GiB\\\",\\\"osName\\\":\\\"OracleLinuxServer\\\"},\\\"version\\\":\\\"no\\\"}\",\"success\":true,\"type\":\"heartbeat\"}";

use std::thread;
use std::time::Duration;
use websockets::{WebSocket, WebSocketWriteHalf};
use std::sync::{Arc, Mutex};

#[tokio::main]
async fn main() {
    // 建立websocket连接
    let ws = WebSocket::connect("wss://push.easydo.plus/wss/client").await.unwrap();

    let (mut r, w) = ws.split();
    let send_str = "{\"clientId\":\"20230312180218-b3c0a4f4-0df6-4cf0-b2a4-5bf2a767ffca\",\"data\":\"{\\\"clientId\\\":\\\"20230312180218-b3c0a4f4-0df6-4cf0-b2a4-5bf2a767ffca\\\",\\\"env\\\":{\\\"PATH\\\":\\\"/usr/java/openjdk-18/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin\\\",\\\"TZ\\\":\\\"Asia/Shanghai\\\",\\\"JAVA_HOME\\\":\\\"/usr/java/openjdk-18\\\",\\\"TERM\\\":\\\"xterm\\\",\\\"JVM_OPTS\\\":\\\"=\\\",\\\"LANG\\\":\\\"C.UTF-8\\\",\\\"HOSTNAME\\\":\\\"d2d2f92f9eb2\\\",\\\"CLIENT_ID\\\":\\\"20230312180218-b3c0a4f4-0df6-4cf0-b2a4-5bf2a767ffca\\\",\\\"PARAMS\\\":\\\"=\\\",\\\"PWD\\\":\\\"/\\\",\\\"JAVA_VERSION\\\":\\\"18.0.1.1\\\",\\\"SHLVL\\\":\\\"1\\\",\\\"HOME\\\":\\\"/root\\\",\\\"_\\\":\\\"/usr/java/openjdk-18/bin/java\\\"},\\\"ip\\\":\\\"223.198.106.81\\\",\\\"port\\\":\\\"33717\\\",\\\"systemInfo\\\":{\\\"cpuInfo\\\":\\\"CPU核心数=4,CPU总的使用率=3990.0,CPU系统使用率=0.0,CPU用户使用率=0.0,CPU当前等待率=0.0,CPU当前空闲率=100.0,CPU利用率=0.0,CPU型号信息='CommonKVMprocessor\\\\n1physicalCPUpackage(s)\\\\n4physicalCPUcore(s)\\\\n4logicalCPU(s)\\\\nIdentifier:Intel64Family15Model6Stepping1\\\\nProcessorID:178BFBFF00000F61\\\\nMicroarchitecture:Netburst\\\",\\\"memory\\\":\\\"Available:14.2GiB/15.5GiB\\\",\\\"osName\\\":\\\"OracleLinuxServer\\\"},\\\"version\\\":\\\"no\\\"}\",\"success\":true,\"type\":\"heartbeat\"}";

    let w_arc = Arc::new(Mutex::new(w));

    // 克隆 Arc<Mutex<WebSocket>>，在线程中共享
    let w_arc_clone = w_arc.clone();

    // 定期推送ping，如果想改这里自己建立信息通讯mpsc::channel(0) 发送不同的数据;
    thread::spawn(move || {
        loop {
            // 这里因为是async自己包裹一下不然跑步起来
            tokio::runtime::Builder::new_multi_thread()
                .enable_all()
                .build()
                .unwrap()
                .block_on(async {
                    // 睡眠定期推送数据
                    thread::sleep(Duration::new(2, 0));
                    let mut w = w_arc_clone.lock().unwrap();
                    w.send_text(send_str.to_string()).await.unwrap();
                });
        }
    });

    // 克隆 Arc<Mutex<WebSocket>>，在线程中共享
    let w_arc_clone = w_arc.clone();
    // 轮训获取服务端信息
    loop {
        // let mut w = w_arc_clone.lock().unwrap();
        let s = r.receive().await.unwrap();
        let (ss, _b, _snake_case) = s.as_text().unwrap();
        println!("Got:{}", ss);
        let mut w = w_arc_clone.lock().unwrap();
        //根据服务端信息进行相关处理
        w.send_text(send_str.to_string()).await.unwrap();
    }
}
