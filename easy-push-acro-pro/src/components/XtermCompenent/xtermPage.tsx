
import { Terminal } from 'xterm';
import { FitAddon } from 'xterm-addon-fit'
import { AttachAddon } from 'xterm-addon-attach';
import 'xterm/css/xterm.css'
import React,{ useEffect } from 'react';


export function newTerminal(fontSize?: number) {
  if (!fontSize || fontSize < 5 || fontSize > 50) {
    fontSize = 13;
  }
  const ua = navigator.userAgent.toLowerCase();
  let lineHeight = 1;
  if (ua.indexOf('windows') !== -1) {
    lineHeight = 1.2;
  }
  return new Terminal({
    // 是否禁用输入
    disableStdin: false,
    cursorStyle: 'underline',
    // 终端中的回滚量
    scrollback: 10000,
    fontSize: fontSize,
    rows: 20,
    // 光标闪烁
    cursorBlink: true,
    theme: {
      //   字体
      foreground: '#ffffff',
      // 光标
      cursor: 'help',
      background: '#1f1b1b'
    },
    fontFamily: 'monaco, Consolas, "Lucida Console", monospace',
    lineHeight: lineHeight,
    rightClickSelectsWord: true,
    logLevel:'debug'
  });
}




export function XtermPage() {

  const term = newTerminal();

  useEffect(() => {
    //   创建实例
    term.open(document.getElementById('xterm-div'))

    term.write("\r\n$ ");

    // 绑定websocket
    const termShellSocketAddress = 'ws://localhost:30002/wss/termShell';
    const xtermWebSocket = new WebSocket(termShellSocketAddress);
    const attachAddon = new AttachAddon(xtermWebSocket)
    term.loadAddon(attachAddon)

    // 进行适应容器元素大小
    const fitAddon = new FitAddon()
    term.loadAddon(fitAddon)
    fitAddon.fit();

    term.focus()

    return () => {
      term.dispose()
    }
  }, [])

  return <div id='xterm-div' style={{height: "100%",width:'100%'}} />
}