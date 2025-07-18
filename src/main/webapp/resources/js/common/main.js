/**
 * New > JavaScript File > main.js
 * (3-3). 자바스크립트에 이벤트 추가 : 햄버거 아이콘을 클릭하면 아래쪽으로 메뉴가 나오도록 main.js에 작성
 */

const toggleBtn = document.querySelector(".navbar_toggleBtn"); //햄버거 버튼 검색
const menu = document.querySelector(".navbar_menu");
const icons = document.querySelector(".navbar_icons");


toggleBtn.addEventListener('click', () => {	//햄버거 버튼 클릭시
	menu.classList.toggle('active');
	icons.classList.toggle('active');
});