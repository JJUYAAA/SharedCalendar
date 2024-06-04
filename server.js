const express = require('express');
const http = require('http');
const path = require('path');
const socketIo = require('socket.io');

const app = express();
const server = http.createServer(app);
const io = socketIo(server);


var username = localStorage.getItem('username');

// 정적 파일 제공을 위한 미들웨어 설정
app.use(express.static(path.join(__dirname, 'public')));

// 루트 URL 요청에 대한 응답 (로그인 페이지)
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'login+.html'));
});

// 로그인 성공 시 index.html로 리다이렉트
app.get('/index.html', (req, res) => {
  res.sendFile(path.join(__dirname, 'index.html'));
});

// 회원가입 페이지 요청에 대한 응답
app.get('/newuser.html', (req, res) => {
  res.sendFile(path.join(__dirname, 'newuser.html'));
});

// 로그인 페이지 요청에 대한 응답
app.get('/login+.html', (req, res) => {
  res.sendFile(path.join(__dirname, 'login+.html'));
});

io.on('connection', (socket) => {
  console.log('새로운 클라이언트가 연결되었습니다.');

  socket.on('message', (msg) => {
    socket.broadcast.emit('message', msg);  // 자신을 제외한 모든 클라이언트에 메시지 전송
  });

  socket.on('disconnect', () => {
    console.log('클라이언트가 연결 해제되었습니다.');
  });
});

const PORT = process.env.PORT || 3000;
server.listen(PORT, () => {
  console.log(`서버가 ${PORT}번 포트에서 실행중입니다.`);
});
