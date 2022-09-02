
const { Client } = require("ssh2");

 
/**
* 描述：连接远程电脑
* 参数：server 远程电脑凭证；then 回调函数
* 回调：then(conn) 连接远程的client对象
*/
function Connect(server, then){
	var conn = new Client();
	conn.on("ready", function(){
        console.log("connect ready");
		then(conn);
	}).on('error', function(err){
		//console.log("connect error!");
	}).on('end', function() {
		console.log("connect end");
	}).on('close', function(had_error){
		console.log("connect close");
	}).connect(server);
}

/**
* 描述：关闭连接
* 参数：
* 回调：then(conn) 连接远程的client对象
*/
function ConnectEnd(conn){
    conn.end()
}


/**
 * 描述：获取远程目录结构信息
*/ 

function getRemoteList(conn,remotePath,then){
    conn.sftp(function(err,sftp){
        sftp.readdir(remotePath,(err,list)=>{
            then(err,list)
        })
    })
}


/**
* 描述：上传文件
* 参数：server 远程电脑凭证；localPath 本地路径；remotePath 远程路径；then 回调函数
* 回调：then(err, result)
*/
function uploadFile(conn, localPath, remotePath, then){
    conn.sftp(function(err,sftp){
        console.log("正在上传..."+localPath)
        sftp.fastPut(localPath,remotePath,then)
    })
}

/**
 * 描述：上传文件
 * 参数：server 远程电脑凭证；localPath 本地路径；remotePath 远程路径；then 回调函数
 * 回调：then(err, result)
 *  const config = {
        host: 'xxx', // 服务器ip
        port: 'xx', // 端口一般默认22
        username: 'xx',
        password: 'xx'
    }
 *  Connect(config,function(conn){
        shell(conn,'ls -l\nexit\n')
    })
 */
function shell(conn,commandStr){
    conn.shell(function(err, stream) {
        if (err) throw err;
        stream.on('close', function() {
          console.log('Stream :: close');
          ConnectEnd(conn)
        }).on('data', function(data) {
          console.log('OUTPUT: ' + data);
        });
        stream.end(commandStr);
    });
}


exports.Connect = Connect;
exports.ConnectEnd = ConnectEnd
exports.getRemoteList = getRemoteList;
exports.uploadFile = uploadFile;
exports.shell = shell;
