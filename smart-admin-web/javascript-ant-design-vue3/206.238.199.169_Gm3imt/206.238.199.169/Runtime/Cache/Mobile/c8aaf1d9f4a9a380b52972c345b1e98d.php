<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <!--    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">-->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="/Public/Static/css/base.css" />
    <link rel="stylesheet" type="text/css" href="/Public/Static/css/nologed.css" />
    <title><?php echo ($webname); ?></title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i&amp;subset=latin-ext" rel="stylesheet">
    <link rel="stylesheet" href="/Public/Dela/demo/demo.css">
    <link rel="stylesheet" href="/Public/Dela/template/dela-template.css">
<!--    <link rel="stylesheet" href="/Public/btn/style.css">-->
    <link rel="stylesheet" href="/Public/build/css/countrySelect.css">

    <style>
        .header_box {
            background: #1b1d29;
            position: fixed;
        }
        .txtl {
            text-align: left;
        }
        .no_headers{
            width: 100%;
            height: 50px;
            z-index: 999;
        }
        .lang-svg {
            padding: 10px 20px 0px 0px;
        }
        button {
            appearance: auto;
            writing-mode: horizontal-tb !important;
            font-style: ;
            font-variant-ligatures: ;
            font-variant-caps: ;
            font-variant-numeric: ;
            font-variant-east-asian: ;
            font-weight: ;
            font-stretch: ;
            font-size: ;
            font-family: ;
            text-rendering: auto;
            color: buttontext;
            letter-spacing: normal;
            word-spacing: normal;
            line-height: normal;
            text-transform: none;
            text-indent: 0px;
            text-shadow: none;
            display: inline-block;
            text-align: center;
            align-items: flex-start;
            cursor: default;
            box-sizing: border-box;
            background-color: buttonface;
            margin: 0em;
            padding: 1px 6px;
            border-width: 2px;
            border-style: outset;
            border-color: buttonborder;
            border-image: initial;
        }
        .btn-hover {
            width: 7em;
            font-size: 16px;
            font-weight: 600;
            color: #fff;
            cursor: pointer;
            height: 30px;
            text-align: center;
            border: none;
            background-size: 300% 100%;
            border-radius: 50px;
            moz-transition: all .4s ease-in-out;
            -o-transition: all .4s ease-in-out;
            -webkit-transition: all .4s ease-in-out;
            transition: all .4s ease-in-out;
        }
        .btn-hover.color-1 {
            background-image: linear-gradient(to right, #25aae1, #40e495, #30dd8a, #2bb673);
            box-shadow: 0 4px 15px 0 rgb(49 196 190 / 75%);
        }
        .btn-hover.color-6 {
            background-image: linear-gradient(to right, #009245, #FCEE21, #00A8C5, #D9E021);
            box-shadow: 0 4px 15px 0 rgb(83 176 57 / 75%);
        }
        .btnl {
            float: left;
            margin-bottom:1.1em;
        }
        .btnr {
            float: right;
            margin-bottom:1.1em;
        }
        .code {
            width: 100% !important;
        }

        .mobile_input {
            width: 70% !important;
            float: right;
        }

        .btn-hover.color-4 {
            background: #1b1d29;
            border-radius: 5px;
            /*box-shadow: 0 4px 15px 0 rgb(252 104 110 / 75%);*/
        }
        .main_demo {
            width: 28% !important;
            float: left;
        }

        .dela-presets-container-2 .dela-preset-2-2 {
            font-size: 16px;
            width: 70%;
            min-width: 95%;
            padding: 0em 0em 7.7em 0em;
            box-shadow: none;
            font-family: Roboto;
            /* background: url(images/background-1-2.svg); */
            background: none;
            background-repeat: no-repeat;
            background-size: cover;
            background-position: center;
        }

        .dela-presets-container-2 .dela-preset-2-2 input[type="email"]:valid, .dela-presets-container-2 .dela-preset-2-2 input[type="password"]:valid, .dela-presets-container-2 .dela-preset-2-2 input[type="text"]:valid {
            border-bottom: none;
            border-radius: 0;
        }

        .form-item {
            /*border-bottom: 0.063em solid rgba(255, 255, 255, 0.2);*/
        }



        .dela-presets-container-2 .dela-preset-2-1 {
            font-size: 16px;
            width: 70%;
            min-width: 95%;
            padding: 0em 0em 7.7em 0em;
            box-shadow: none;
            font-family: Roboto;
            /* background: url(images/background-1-2.svg); */
            background: none;
            background-repeat: no-repeat;
            background-size: cover;
            background-position: center;
        }

        .pml::placeholder {
            /*padding: 0 10px !important;*/
            color: #aaaaaa;
        }
        .pml::placeholder {
            /*padding: 0 10px !important;*/
            color: #aaaaaa;
        }

        .pml {
            text-indent:10px;
        }

        .dela-presets-container-2 {
            /*background: url(images/main-background-2.svg) no-repeat; */
            background: #1b1d29;
            background-size: 100em auto;
            background-position: top;
            padding: 6em 0;
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            align-items: flex-start;
        }

        body{
            background-color: #1b1d29 !important;
            background: #1b1d29 !important;
            min-height: 100vh;
        }

        .dfs {
            color: #fff;
            margin: 2px 16px 2px 0px;
            padding: 0px 0px 5px 0px;

        }
        .fsl {
            border-bottom: 1px solid #fff;
            font-weight: bold;
        }

        .fsr {
            color: #aaaaaa;
        }

        .fd-box {
            width: 200px;
            height: 80px;
            line-height: 80px;
        }


        .dela-presets-container-2 .dela-preset-container{
            margin: 2.7em 0em 0em 0em;
            display: flex;
            justify-content: center;
            width: 95%;
        }

        .dela-presets-container-2 .dela-preset-2-1 input[type="submit"] {
            margin: 0 auto;
            padding: 0;
            display: block;
            color: #1b1d29;
            font-family: Roboto;
            font-size: 1em;
            font-weight: bold;
            width: 100%;
            min-height: 2.71em;
            background: none;
            border: 0.063em solid #fff;
            border-radius: 0.6em;
            cursor: pointer;
            /* margin-top: 2.1em; */
            -webkit-appearance: none;
            background: #c1c1c1;
        }

        .dela-presets-container-2 .dela-preset-2-1 input[type="email"], .dela-presets-container-2 .dela-preset-2-1 input[type="password"], .dela-presets-container-2 .dela-preset-2-1 input[type="text"]
        {
            color: #fff;
            font-family: Roboto;
            font-size: 1em;
            font-weight: 200;
            width: 100%;
            background: none;
            border: none;
            padding: 0.7em 0;
            border: 1px solid #212332;
            margin-bottom: 1.3em;
            border-radius: 0;
            outline: none;
            border-radius: 5px;
            background: #212332;
        }

        .dela-presets-container-2 .dela-preset-2-1 .dela-form__rorgot-password {
            display: block;
            margin-top: 1.5em;
            text-align: left;
            padding: 0;
        }

        .dela-presets-container-2 .dela-preset-2-2 input[type="email"], .dela-presets-container-2 .dela-preset-2-2 input[type="password"], .dela-presets-container-2 .dela-preset-2-2 input[type="text"] {
            color: #fff;
            font-family: Roboto;
            font-size: 1em;
            font-weight: 200;
            width: 100%;
            background: none;
            border: none;
            padding: 0.7em 0;
            border: 1px solid #212332;
            margin-bottom: 1.3em;
            border-radius: 0;
            outline: none;
            border-radius: 5px;
            background: #212332;
        }

        #country_selector {
            margin-left: 46px;
            border-radius: 5px;
            height: 43.39px;
        }

        .country-select {
            width: 87.5%;
        }
        .dela-presets-container-2 .dela-preset-2-2 input[type="submit"] {
            margin: 0 auto;
            padding: 0;
            display: block;
            color: #1b1d29;
            font-family: Roboto;
            font-size: 1em;
            font-weight: bold;
            width: 100%;
            min-height: 2.71em;
            background: none;
            border: 0.063em solid #fff;
            border-radius: 0.6em;
            cursor: pointer;
            /* margin-top: 2.1em; */
            -webkit-appearance: none;
            background: #c1c1c1;
        }


    </style>
</head>
<body>

<div class="no_headers header_box" >
    <div class="fl bhalf allhg txtl" style="line-height:50px;">
        <svg onclick="uplogin()" style="padding: 10px  0px 0px 10px;" width="26" height="26" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><rect width="48" height="48" fill="white" fill-opacity="0.01"/><path d="M31 36L19 24L31 12" stroke="#FDFDFD" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/></svg>
<!--        <i class="bi bi-x fw"  onclick="goindex()" style="font-size:36px;color: #fff"></i>-->
    </div>
    <div class="fr bhalf allhg txtr" style="line-height:50px;">
        <a href="<?php echo u('Login/setlang');?>">
            <svg t="1654176737678" class="lang-svg" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="34611" width="26" ><path d="M782 912H242c-71.68 0-130-58.32-130-130V242c0-71.68 58.32-130 130-130h540c71.68 0 130 58.32 130 130v540c0 71.68-58.32 130-130 130zM242 172c-38.59 0-70 31.41-70 70v540c0 38.59 31.41 70 70 70h540c38.59 0 70-31.41 70-70V242c0-38.59-31.41-70-70-70H242z" p-id="34612" fill="#ffffff"></path><path d="M455.49 621.78c-3.97-1.08-8.51-1.71-13.51-1.9-4.32-0.15-8.84-0.21-13.6-0.21h-100.5v-86.64h107.87c9.01 0 15.72-1.65 20.48-4.99 4.09-2.86 8.98-8.65 8.98-20.14 0-9.2-2.82-16.12-8.37-20.56-4.91-3.93-11.8-5.86-21.09-5.86H327.89V404.8h112.2c8.39 0 14.92-1.69 19.96-5.14 4.34-2.94 9.51-8.88 9.5-20.98-0.48-9.18-3.66-15.97-9.53-20.18-4.96-3.53-11.48-5.25-19.93-5.25H302.76c-10.47 0-18.01 2.31-23.07 7.09-5.17 4.93-7.69 12.25-7.69 22.36v259.06c0 11.11 3.15 19.06 9.36 23.65 5.28 3.91 12.28 5.82 21.4 5.82h139.93c9.38 0 16.37-1.54 21.44-4.74 4.45-2.83 9.75-8.84 9.75-21.26 0-8.19-3.11-13.24-5.7-16.01-3.26-3.53-7.52-6.03-12.69-7.44zM745.3 356.36c-4.74-5.25-11.96-7.91-21.46-7.91-9.85 0-17.18 3.02-21.79 8.99-3.95 5.1-5.94 11.4-5.94 18.74v188.1L559.8 364.97c-2.85-3.45-5.68-6.6-8.5-9.41-3.24-3.24-9.06-7.11-18.51-7.11-11.87 0-17.89 5.16-20.85 9.5-3.42 4.99-5.14 11.13-5.14 18.23v271.21c0 7.47 2.22 13.94 6.59 19.23 3.36 4.06 9.75 8.93 21.13 8.93 11.23 0 17.72-4.74 21.17-8.72 4.64-5.33 6.99-11.89 6.99-19.44v-184.6l135.35 195.08c3.18 4.61 6.73 8.5 10.59 11.63 5.01 4.02 10.86 6.05 17.39 6.05 11.9 0 17.92-5.18 20.85-9.5 3.42-4.99 5.14-11.13 5.14-18.23V376.19c0-8.19-2.24-14.85-6.7-19.83z" p-id="34613" fill="#ffffff"></path></svg>
        </a>
    </div>
</div>
<div id="page-container">
    <div class="all-dela-presets-container"  >
        <!-- Preset Begin-->
        <div class="dela-presets-container-2" style="padding: 0px;">
            <div class="dela-preset-container">
                <form class="dela-preset-2-2" onclick="return false" method="get">
<!--                    <p class="dela-form__title"><?php echo L('注册账号');?></p>-->


                    <div class="fd-box">
                        <span class="fsl dfs spanchl" onclick="stype(1)"><?php echo L('手机');?></span>
                        <span class="fsr dfs spanchr" onclick="stype(2)"><?php echo L('邮箱reg');?></span>

                    </div>


                    <input type="hidden" name="type" id="type" value="1">
                    <input type="hidden" name="account" id="account" >
                    <input type="email" class="pml" name="email" id="email" placeholder="E-mail" required="" autocomplete="email" style="display: none">
                    <div id="main_demo" style="margin-bottom:1.1em;display: block" >
                        <div class="container">
                            <div class="form-item">
                                <input id="country_selector" class="pml" type="text">
                                <label for="country_selector" style="display:none;">Select a country here...</label>
                            </div>
                            <button type="submit" style="display:none;"><?php echo L('提交');?></button>
                        </div>
                    </div>
                    <input type="password" class="pml" name="password" id="lpwd" placeholder="Password" required="">
                    <input type="password"  class="pml" name="repeat" id="rlpwd"  placeholder="Repeat Password" oninput="this.setCustomValidity(this.value != document.getElementById('pass-'+2).value ? 'Passwords are not the same.' : '')" required="">
                    <input type="text"  class="pml" id="invit" name="invite"  placeholder="Invite Code" required="" value="<?php echo ($qrcode); ?>" autocomplete="invite">
                    <div id="yxyzm11" name="yxyzm11">
                        <div style="background: #212332;width: 100%;height: 44px;border-radius: 5px ;">

                            <input type="text" class="pml fl" id="ecode"  name="code" placeholder="Code"  class="code"  required=""   style="width: 70%;border-radius:  5px 0 0 5px;border: none" >
                            <input type="submit" class="pml fl fbg" onclick="emailsend();" value="<?php echo L('提交');?>"  name="code" placeholder="Send"  class="send-btn"  required=""   style="width: 30%;border-radius: 0 5px 5px 0; text-align: center;background: #212332; border: none; color: #3db485;margin-bottom: 1.3em;border: 1px solid #212332;height: 23px;" >
                        </div>
                    </div>
                    <input type="submit" name="submit" class="fbg"  onclick="upreg()" value="<?php echo L('注册');?>">

                </form>
            </div>
        </div>

    </div>
</div>

<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/Public/Static/js/layer/layer.js" ></script>
<script type="text/javascript">
    function upreg(){
        var  type = $('#type').val();
        if (type != 1 && type != 2) {
            layer.msg("<?php echo L('请选择注册类型');?>");return false;
        }
        // console.log(type);
        // return false;

        if (type == 1) {
            var account = $("#country_selector").val();
            if(account=='' || account == null){
                layer.msg("<?php echo L('请输入密码');?>");return false;
            }
        }
        if (type == 2) {
            var email = $("#email").val();
            var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
            if(email=='' || email == null){
                layer.msg("<?php echo L('请输入邮箱');?>");return false;
            }
            if(!reg.test(email)){
                layer.msg("<?php echo L('邮箱格式不正确');?>");return false;
            }
            var account = email;
       
        
        var ecode = $("#ecode").val();
        if(ecode == ''){
            layer.msg("<?php echo L('请输入验证码');?>");return false;
        }
        }
        var lpwd = $("#lpwd").val();
        if(lpwd == ''){
            layer.msg("<?php echo L('请输入密码');?>");return false;
        }

        var rlpwd = $("#rlpwd").val();
        if(lpwd != rlpwd){
            layer.msg("<?php echo L('重复密码不正确');?>");return false;
        }
        var invit = $("#invit").val();
        if( invit == ''){
            layer.msg("<?php echo L('请输入邀请码');?>");return false;
        }


        console.log(type)
        console.log(account)
        console.log(ecode)
        console.log(lpwd)
        console.log(invit)


        $.post("<?php echo U('Login/upregister');?>",
            {'account' : account, 'ecode' : ecode, 'lpwd' : lpwd, 'invit' : invit, 'type' : type},
            function(data){
                if(data.code == 1){
                    layer.msg(data.info);
                    setTimeout(function(){
                        window.location.href="<?php echo U('Login/index');?>";
                    },2000);
                }else{
                    layer.msg(data.info);return false;
                }
            }
        );


    }
</script>
<script type="text/javascript">
    function emailsend(){
        var  type = $('#type').val();
        if(type == 1){
             var email = $("#country_selector").val();
        }else{
        var email = $("#email").val();
            
        }
        var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
            if (type == 2) {
        if(email=='' || email == null){
            layer.msg("<?php echo L('请输入邮箱');?>");return false;       
        }
        if(!reg.test(email)){
            layer.msg("<?php echo L('邮箱格式不正确');?>");return false; 
        }
            }
       
        $.post("<?php echo U('Login/sendcode');?>",
        {'email':email,'type':type},
       
        function(data){
            if(data.code == 1){
                layer.msg(data.info);
                var obj = $("#sendsms");
                var strobj = $("#smsstr");
                var t = 60;
                var interval = setInterval(function() {
                    obj.removeAttr('onclick');
                    strobj.text(t + "<?php echo L('秒后再发送');?>");
                    t--;
                    if(t < 1){
                        obj.attr("onclick","emailsend();");
                        clearInterval(interval);
                        strobj.text("<?php echo L('获取验证码');?>");
                    }
                },1000);
            }else{
                layer.msg(data.info);
                $("#sendsms").attr("onclick","emailsend();");
                $("#smsstr").text("<?php echo L('获取验证码');?>");
                $("#verifycode").click();
            }
        });
    }
</script>

<script type="text/javascript">
    
    function stype(type) {

        $('.dfs').removeClass('fsl');
        $('.dfs').removeClass('fsr');

        if (type == 2) {
            $('#email').show();
           // $('#yxyzm11').show();
            $('#main_demo').hide();
            $('.spanchr').addClass('fsl');
            $('.spanchl').addClass('fsr');
        }
        if (type == 1) {
            $('#email').hide(); 
            
            $('#yxyzm11').show();
          //  $('#yxyzm11').hide();
            $('#main_demo').show();

            $('.spanchl').addClass('fsl');
            $('.spanchr').addClass('fsr');
        }
        $('#type').val(type);

    }
    
    function goindex(){
        window.location.href="<?php echo U('Index/index');?>";
    }

    function uplogin(){
        window.location.href="<?php echo U('Login/index');?>";
    }

    function forgot_password() {
        window.location.href="<?php echo U('Login/findpwd');?>";
    }


</script>
<script src="/Public/build/js/jquery-1.9.1.min.js"></script>
<script src="/Public/build/js/countrySelect.js"></script>
<script>
    $("#country_selector").countrySelect({
        preferredCountries: ["jp","us"]
        // preferredCountries: ["us","jp","kr","vn"]
    });
</script>

</body>
</html>