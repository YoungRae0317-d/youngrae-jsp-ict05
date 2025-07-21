/**
 * 
 */

//회원가입, 수정시의 이메일 체크
function modify_selectEmailChk(){
   if(document.modifyform.user_email3.value != 0) {
      document.modifyform.user_email2.value = document.modifyform.user_email3.value;
      return false;
   }else{
      document.modifyform.user_email2.value = "";
      document.modifyform.user_email2.focus();
      return false;
   }
}

function modifyCheck(){
	
	//비밀번호 불일치
}