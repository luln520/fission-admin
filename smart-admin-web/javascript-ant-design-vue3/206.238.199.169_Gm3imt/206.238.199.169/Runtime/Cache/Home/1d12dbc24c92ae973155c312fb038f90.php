<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width,minimum-scale=1,maximum-scale=1.0,initial-scale=1,user-scalable=no,viewport-fit=true" data-shuvi-head="true">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">	
	    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
	    <link rel="stylesheet" type="text/css" href="/Public/Home/static/css/base.css" />
	    <title><?php echo ($webname); ?></title>
        <style>
            .css-ogtd7z {
                box-sizing: border-box;
                margin: 0px;
                min-width: 0px;
                display: flex;
                transition: all 1s ease 0s;
                -webkit-box-pack: center;
                justify-content: center;
                background-color: rgb(254, 241, 242);
            }
            .css-jrzkh7 {
                box-sizing: border-box;
                margin: 0px;
                min-width: 0px;
                background-color: rgb(24, 26, 32);
            }
            .css-1aac2e {
                box-sizing: border-box;
                margin: 0px auto;
                min-width: 0px;
                padding-left: 24px;
                padding-right: 24px;
                max-width: 1248px;
                background-color: rgb(254, 241, 242);
            }
            .css-1wr4jig {
                box-sizing: border-box;
                margin: 0px;
                min-width: 0px;
                display: flex;
                flex-direction: column;
                flex: 1 1 0%;
            }
            .css-1ay57iv {
                box-sizing: border-box;
                margin: 0px;
                min-width: 0px;
                display: flex;
                background-color: rgb(250, 250, 250);
                flex: 1 1 0%;
                flex-direction: column;
            }
            .css-8hzjvg {
                box-sizing: border-box;
                margin: 0px;
                min-width: 0px;
                padding: 24px 16px;
                padding-left: 24px;
                padding-right: 24px;
            }
            .css-1eklhqk {
                box-sizing: border-box;
                min-width: 0px;
                width: 100%;
                max-width: 1200px;
                margin: 0px auto;
            }
            .css-190uhut {
                box-sizing: border-box;
                margin: 0px;
                min-width: 0px;
                display: flex;
                -webkit-box-align: center;
                align-items: center;
                -webkit-box-pack: justify;
                justify-content: space-between;
            }
            .css-11y6cix {
                box-sizing: border-box;
                margin: 0px;
                min-width: 0px;
                display: flex;
                -webkit-box-align: center;
                align-items: center;
                flex: 1 1 0%;
            }
            .css-146agw4 {
                box-sizing: border-box;
                margin: 0px;
                min-width: 0px;
                color: #00b897;
                font-size: 24px;
                fill: #00b897;
                cursor: pointer;
                width: 32px;
                height: 32px;
                font-size: 32px;
                margin-left: -48px;
            }
            .css-1djsyd6 {
                box-sizing: border-box;
                margin: 0px 0px 0px 16px;
                min-width: 0px;
                font-weight: 600;
                font-size: 24px;
                line-height: 32px;
            }
            .css-1dihobw {
                box-sizing: border-box;
                margin: 0px;
                min-width: 0px;
                flex: 1 1 0%;
                background-color: rgb(255, 255, 255);
                border-radius: 40px 40px 0px 0px;
                padding: 24px 16px;
                padding-left: 24px;
                padding-right: 24px;
            }
            .css-1eklhqk {
                box-sizing: border-box;
                min-width: 0px;
                width: 100%;
                max-width: 1200px;
                margin: 0px auto;
            }
            .css-tu2ioc {
                box-sizing: border-box;
                margin: 0px;
                min-width: 0px;
                display: flex;
                padding-top: 0px;
                padding-bottom: 0px;
                flex-flow: column wrap;
                padding-top: 16px;
                padding-bottom: 16px;
                flex-direction: row;
            }
            .css-1ekpz1z {
                box-sizing: border-box;
                margin: 0px;
                min-width: 0px;
                order: 1;
                flex: 1 1 0%;
            }
            .czbox{width:100%;height:80px;margin-bottom:20px;}
            .czbox_1{width:30%;height:80px;float:left;}
            .czbox_2{width:70%;height:80px;float:right;}
            .czbox_3{width:100%;height:20px;line-height:20px;}
            .czbox_4{width:100%;height:50px;line-height:50px;margin-top:5px;border:1px solid #f5f5f5;padding-left:15px;}
            .czbox_4:hover{border:1px solid #FCD535;}
            .czbox_5{width:100%;height:100%;border:1px solid #fff;background-color:#fff;color:#000;font-size:14px;}
            .czbox_5:hover{border:1px solid #fff;background-color:#fff;}
            input:focus{border:1px solid #fff;background-color:#fff;}
            .layui-upload-file {
                display: none!important;
                opacity: .01;
            }
            .czbox_6{width:100%;height:50px;line-height:50px;margin-top:5px;}
        </style>
	</head>
	<body>
	    <div class="App">
	        <div class="css-tq0shg">
	            <header class="css-jmskxt">
	 <a href="<?php echo U('Index/index');?>" clss="css-1mvf8us">
	     <img src="/Upload/public/<?php echo get_config('waplogo');?>" class="css-1jgk2rg" style="height:35px;width:35px;border-radius:5px;margin-left: 10px;" />
	 </a>
	<div class="css-1tp5kus header-title">
		<div class="css-vurnku  f22 fw header-title"><?php echo get_config('webname');?></div>
	</div>
	 <div class="css-1tp5kus">
	     <a href="<?php echo U('Index/index');?>" class="css-1smf7ma">
	         <div class="css-vurnku"><?php echo L('首页');?></div>
	     </a>
	 </div>

	<div class="css-1tp5kus buy_coins">
		<!--	<ul class="buy-sub" style="padding:0px;display:none;z-index: 999999;">
	
			<div class="order_navlist buy_navlist">
				<li  class="li-sub" style="list-style:none;">
					<a href="https://banxa.com/">
						<div class="optionli">
							<svg width="18" height="18" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M14 13V9.00001C14 7.89544 14.8954 7.00001 16 7.00001H42C43.1046 7.00001 44 7.89544 44 9.00001V27C44 28.1046 43.1046 29 42 29H40" stroke="#116947" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/><rect x="4" y="19" width="30" height="22" rx="2" fill="#00b897" stroke="#116947" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/><path d="M4 28L34 28" stroke="#116947" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/><path d="M34 23L34 35" stroke="#116947" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/><path d="M4 23L4 35" stroke="#116947" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/><path d="M11 34L19 34" stroke="#116947" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/><path d="M25 34L27 34" stroke="#116947" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/></svg>&nbsp;
							<span class="f16"  style="color:#EAECEF;"> Banxa</span>
						</div>
					</a>
				</li>
				<li  class="li-sub"  style="list-style:none;">
					<a href="https://www.simplex.com/account/buy">
						<div class="optionli">
							<svg width="18" height="18" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><rect width="48" height="48" fill="white" fill-opacity="0.01"/><path d="M4 10C4 8.89543 4.89543 8 6 8H42C43.1046 8 44 8.89543 44 10V38C44 39.1046 43.1046 40 42 40H6C4.89543 40 4 39.1046 4 38V10Z" fill="#00b897" stroke="#147c52" stroke-width="3" stroke-linejoin="bevel"/><path d="M4 16H44" stroke="#147c52" stroke-width="3" stroke-linecap="butt" stroke-linejoin="bevel"/><path d="M27 32H36" stroke="#147c52" stroke-width="3" stroke-linecap="butt" stroke-linejoin="bevel"/><path d="M44 10V26" stroke="#147c52" stroke-width="3" stroke-linecap="butt" stroke-linejoin="bevel"/><path d="M4 10V26" stroke="#147c52" stroke-width="3" stroke-linecap="butt" stroke-linejoin="bevel"/></svg>&nbsp;
							<span class="f16"   style="color:#EAECEF;"> Simplex</span>
						</div>
					</a>
				</li>
				<li  class="li-sub"  style="list-style:none;">
					<a href="https://www.simplex.com/account/buy">
						<div class="optionli">
							<svg width="18" height="18" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><rect width="48" height="48" fill="white" fill-opacity="0.01"/><path d="M44 18V8H4V18" stroke="#147c52" stroke-width="3" stroke-linecap="butt" stroke-linejoin="bevel"/><rect x="4" y="18" width="40" height="22" fill="#00b897" stroke="#147c52" stroke-width="3" stroke-linejoin="bevel"/><path d="M12 29H14" stroke="#147c52" stroke-width="3" stroke-linecap="butt" stroke-linejoin="bevel"/><path d="M20 29H22" stroke="#147c52" stroke-width="3" stroke-linecap="butt" stroke-linejoin="bevel"/><path d="M28 29H30" stroke="#147c52" stroke-width="3" stroke-linecap="butt" stroke-linejoin="bevel"/></svg>&nbsp;
							<span class="f16"   style="color:#EAECEF;"> Ramp</span>
						</div>
					</a>
				</li>  
			</div>
		</ul>-->
		<a href="<?php echo U('Finance/index');?>" class="css-1smf7ma">
			<div class="css-vurnku"><?php echo L('充币');?>
				<i class="bi bi-caret-down-fill css-1x1srvk" style="font-size:12px;"></i>
			</div>
		</a>
	</div>

	
	<div class="css-1tp5kus trad_coins">
		<ul class="trad-sub" style="padding:0px;display:none;z-index: 999999;">
			<!--<div class="order_navlist" style="transform: translate(972px, 64px);">-->
			<div class="order_navlist trad_navlist">
				<li  class="li-sub" style="list-style:none;">
					<a href="<?php echo U('Contract/index');?>">
						<div class="optionli">
							<svg width="18" height="18" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><rect width="48" height="48" fill="white" fill-opacity="0.01"/><path d="M4 44H44" stroke="#076338" stroke-width="3" stroke-linecap="butt" stroke-linejoin="bevel"/><path d="M4 26L12 28V38H4V26Z" fill="#00b897" stroke="#076338" stroke-width="3" stroke-linejoin="bevel"/><path d="M20 24L28 20V38H20V24Z" fill="#00b897" stroke="#076338" stroke-width="3" stroke-linejoin="bevel"/><path d="M36 16L44 12V38H36V16Z" fill="#00b897" stroke="#076338" stroke-width="3" stroke-linejoin="bevel"/><path d="M4 18L12 20L44 4H34" stroke="#076338" stroke-width="3" stroke-linecap="butt" stroke-linejoin="bevel"/></svg>
							&nbsp;
							<span class="f16" style="color:#EAECEF;"> <?php echo L('秒合约');?><img src="/Public/Home/static/imgs/hot-2.svg" style=" float: right;
    margin-top: 15px;"></span>
						</div>
					</a>
				</li>
				<li  class="li-sub"  style="list-style:none;">
					<a href="<?php echo U('Trade/index');?>">
						<div class="optionli">
							<svg width="18" height="18" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><rect width="48" height="48" fill="white" fill-opacity="0.01"/><circle cx="24" cy="24" r="20" fill="#00b897" stroke="#076338" stroke-width="3" stroke-linecap="butt" stroke-linejoin="bevel"/><path d="M20 16H25H27C29.2091 16 31 17.7909 31 20C31 22.2091 29.2091 24 27 24H20V16Z" fill="#00b897"/><path d="M20 16V24H27C29.2091 24 31 22.2091 31 20V20C31 17.7909 29.2091 16 27 16H25M20 16H16M20 16V12M20 16H25M25 16V12" stroke="#076338" stroke-width="3" stroke-linecap="butt" stroke-linejoin="bevel"/><path d="M20 24H29C31.2091 24 33 25.7909 33 28C33 30.2091 31.2091 32 29 32H25H20V24Z" fill="#00b897"/><path d="M20 32V24H29C31.2091 24 33 25.7909 33 28V28C33 30.2091 31.2091 32 29 32H25M20 32V36M20 32H16H25M20 32H25M25 32V36" stroke="#076338" stroke-width="3" stroke-linecap="butt" stroke-linejoin="bevel"/></svg>
							&nbsp;
							<span class="f16"   style="color:#EAECEF;"> <?php echo L('币币交易');?></span>
						</div>
					</a>
				</li>
			</div>
		</ul>
		<a href="<?php echo U('Index/index');?>" class="css-1smf7ma">
			<div class="css-vurnku"><?php echo L('交易');?>
				<i class="bi bi-caret-down-fill css-1x1srvk" style="font-size:12px;"></i>
			</div>
		</a>
	</div>

	 <div class="css-1tp5kus">
	     <a href="<?php echo U('Issue/index');?>" class="css-1smf7ma">
	         <div class="css-vurnku"><img src="/Public/Home/static/imgs/hot-2.svg" class="hot-2">IEO</div>
	     </a>
	 </div>
	 <div class="css-1tp5kus">
	     <a href="<?php echo U('Orepool/index');?>" class="css-1smf7ma">
	         <div class="css-vurnku"><img src="/Public/Home/static/imgs/hot-2.svg" class="hot-2">DeFi</div>
	     </a>
	 </div>
	 <div class="css-1tp5kus">
	     <a href="<?php echo U('Index/gglist');?>" class="css-1smf7ma">
	         <div class="css-vurnku"><?php echo L('公告中心');?></div>
	     </a>
	 </div>
	 <div class="css-11y6cix"></div>
	 
	 <ul id="nav" class="nav">
	 <div class="css-wu6zme">
	     
	     <?php if($uid != 0): ?><div class="css-mu7imd nav">
	         <div class="css-1smf7ma">
	             <a href="<?php echo U('Finance/index');?>">
	             <div class="css-15owl46"  style="padding: 0px 5px;">
	                 <div class="css-vurnku" style="color:#fff;"><?php echo L('钱包');?></div>
	             </div>
	             </a>
	         </div>
	         <div class="css-1smf7ma">
	             <li class="nLi">
	                 <div class="css-15owl46"  style="padding: 0px 5px;">
	                     <div class="css-vurnku"><?php echo L('订单');?>
	                         <i class="bi bi-caret-down-fill css-1x1srvk" style="font-size:12px;"></i>
	                     </div>
	                 </div>
	                 <ul class="sub" style="padding:0px;display:none;z-index: 999999;">
	                 <!--<div class="order_navlist" style="transform: translate(972px, 64px);">-->
	                 <div class="order_navlist" style="top: 50px;right:180px;">
	                     
	                         <li style="list-style:none;">
	                             <a href="<?php echo U('Trade/bborder');?>">
	                             <div class="optionli">
	                                 <i class="bi bi-ui-checks" style="color:#00b897;font-size:18px;"></i>
	                                 <span style="color:#EAECEF;"><?php echo L('币币交易订单');?></span>
	                             </div>
	                             </a>
	                         </li>
	                         <li  style="list-style:none;">
	                             <a href="<?php echo U('Contract/contractjc');?>">
	                             <div class="optionli">
	                                 <i class="bi bi-sliders" style="color:#00b897;font-size:18px;"></i>
	                                 <span  style="color:#EAECEF;"><?php echo L('合约交易订单');?></span>
	                             </div>
	                             </a>
	                         </li>
	                         <li  style="list-style:none;">
	                             <a href="<?php echo U('Issue/normalissue');?>">
	                             <div class="optionli">
	                                 <i class="bi bi-ui-radios" style="color:#00b897;font-size:18px;"></i>
	                                 <span  style="color:#EAECEF;"><?php echo L('认购订单');?></span>
	                             </div>
	                             </a>
	                         </li>
	                         <li  style="list-style:none;">
	                             <a href="<?php echo U('Orepool/normalmin');?>">
	                             <div class="optionli">
	                                 <i class="bi bi-hammer" style="color:#00b897;font-size:18px;"></i>
	                                 <span  style="color:#EAECEF;"><?php echo L('矿机订单');?></span>
	                             </div>
	                             </a>
	                         </li>
	                     
	                 </div>
	                 </ul>
	             </li>
	         </div>
	         
	         
	         <div class="css-1smf7ma">
	             <li class="nLi">
	                 <div class="css-15owl46" style="padding: 0px 5px;">
	                     <div class="css-vurnku">
	                         <a href="<?php echo U('User/index');?>" style="color: #fff;">
	                         <i class="bi bi-person-circle" style="font-size:20px;"></i>
	                         </a>
	                     </div>
	                 </div>
	                 <ul class="sub" style="padding:0px;display:none;z-index: 999999;">
	                 <div class="order_navlist" style="min-width:220px;top: 60px;right:60px;">
	                     
	                         <li style="list-style:none;">
	                             <div style="width:100%;height:50px;line-height:50px;text-align:left;padding:0px 15px;">
	                                 <span style="font-size:16px;font-weight:bold;color:#EAECEF;"><?php echo ($username); ?></span>
	                                 <?php if($rzstatus == 2): ?><span class="f12 fgreen"><?php echo L('已认证');?></span>
	                                 <?php else: ?>
	                                 <span class="f12 fred"><?php echo L('未认证');?></span><?php endif; ?>
	                             </div>
	                         </li>

	                         <li  style="list-style:none;">
	                             <a href="<?php echo U('User/index');?>">
	                             <div class="optionli">
	                                 <i class="bi bi-person-fill" style="color:#00b897;font-size:18px;"></i>
	                                 <span  style="color:#EAECEF;"><?php echo L('账户总览');?></span>
	                             </div>
	                             </a>
	                         </li>
	                         <li  style="list-style:none;">
	                             <a href="<?php echo U('User/respwd');?>">
	                             <div class="optionli">
	                                 <i class="bi bi-gear" style="color:#00b897;font-size:18px;"></i>
	                                 <span  style="color:#EAECEF;"><?php echo L('安全设置');?></span>
	                             </div>
	                             </a>
	                         </li>
	                         <li  style="list-style:none;">
	                             <a href="<?php echo U('User/authrz');?>">
	                             <div class="optionli">
	                                 <i class="bi bi-shield-fill" style="color:#00b897;font-size:18px;"></i>
	                                 <span  style="color:#EAECEF;"><?php echo L('身份认证');?></span>
	                             </div>
	                             </a>
	                         </li>
	                         <li  style="list-style:none;">
	                             <a href="<?php echo U('User/tgcode');?>">
	                             <div class="optionli">
	                                 <i class="bi bi-person-plus-fill" style="color:#00b897;font-size:18px;"></i>
	                                 <span  style="color:#EAECEF;"><?php echo L('推荐返佣');?></span>
	                             </div>
	                             </a>
	                         </li>
	                         
	                         <li  style="list-style:none;">
	                             <a href="<?php echo U('Login/loginout');?>">
	                             <div class="optionli" style="border-top:1px solid #f5f5f5;">
	                                 <i class="bi bi-box-arrow-right" style="color:#00b897;font-size:18px;"></i>
	                                 <span  style="color:#EAECEF;"><?php echo L('退出账号');?></span>
	                             </div>
	                             </a>
	                         </li>
	                     
	                 </div>
	                 </ul>
	                 
	             </li>
	             
	         </div>
	         <div class="css-1smf7ma">
	             
	             <div class="css-15owl46" style="padding: 0px 5px;"> 
	                 <a href="<?php echo U('User/notice');?>" style="color: #fff;">
	                 <div class="css-vurnku">
	                     <i class="bi bi-bell css-6px2js" style="font-size:20px;"></i>
	                     <?php if($sum >= 1): ?><div class="css-1rch7es"><?php echo ($sum); ?></div><?php endif; ?>
	                 </div>
	                 </a>
	             </div>
	         </div>
	     </div>
	     <?php else: ?>
	     <div class="css-mu7imd">
	         <a href="<?php echo U('Login/index');?>">
	         <div class="css-1smf7ma">
	             <div class="css-15owl46"  style="padding: 0px 5px;">
	                 <div class="css-vurnku"><?php echo L('登陆');?></div>
	                 
	             </div>
	         </div>
	         </a>
	     </div>
	     <div class="css-mu7imd" style="border: 2px solid #00b897;border-radius: 5px;">
	         <a href="<?php echo U('Login/register');?>">
	         <div class="css-1smf7ma">
	             <div class="css-15owl46"  style="padding: 0px 5px;">
	                 <div class="css-vurnku" style="color: #00b897"><?php echo L('注册');?></div>
	             </div>
	         </div>
	         </a>
	     </div><?php endif; ?>
	     
	 </div>

	 <div class="css-wu6zme">
	     <li class="nLi">   <a href="/app"> 
	     <div class="css-1ql2hru" style="padding: 0px 5px;">
	         <div class="css-1smf7ma"><svg width="18" height="18" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><rect width="36" height="36" fill="white" fill-opacity="0.01"/><path d="M24.0083 33.8995V6" stroke="#ffffff" stroke-width="4" stroke-linecap="butt" stroke-linejoin="bevel"/><path d="M36 22L24 34L12 22" stroke="#ffffff" stroke-width="4" stroke-linecap="butt" stroke-linejoin="bevel"/><path d="M36 42H12" stroke="#ffffff" stroke-width="4" stroke-linecap="butt" stroke-linejoin="bevel"/></svg></div>
	         <ul class="sub" style="padding:0px;display:none;z-index: 999999;">
	         <div class="order_navlist" style="min-width:220px;top: 50px;right: 60px;">
	                 <div style="width:140px;height:140px;margin:10px auto;">
	                     <img src="/app/static/code.png" style="width:100%;border-radius:10px;" />
	                 </div>
	                 <div style="width:100%;height:30px;line-height:30px;text-align:center;">
	                     <span style="color:#fff;font-size:12px;"><?php echo L('自动识别设备');?></span>
	                 </div>
	             </div>
	         </ul>
	     </div></a>
	     </li>
	     <li class="nLi">
			 <div class="css-1ql2hru" style="padding: 0px 5px;">

				 <?php if(LANG_SET=='zh-cn'){?>
				 <div class="css-1smf7ma">中文简体&nbsp;&nbsp;</div>
				 <?php }elseif(LANG_SET=='en-us'){?>
				 <div class="css-1smf7ma">English&nbsp;&nbsp;</div>
				 <?php }elseif(LANG_SET=='fr-fr'){?>
				 <div class="css-1smf7ma">Français&nbsp;&nbsp;</div>
				 <?php }elseif(LANG_SET=='de-de'){?>
				 <div class="css-1smf7ma">Deutsch&nbsp;&nbsp;</div>
				 <?php }elseif(LANG_SET=='it-it'){?>
				 <div class="css-1smf7ma">Italiano&nbsp;&nbsp;</div>
				 <?php }elseif(LANG_SET=='ja-jp'){?>
				 <div class="css-1smf7ma">日本語&nbsp;&nbsp;</div>
				 <?php }elseif(LANG_SET=='ko-kr'){?>
				 <div class="css-1smf7ma">한국어&nbsp;&nbsp;</div>
				 <?php }elseif(LANG_SET=='tr-tr'){?>
				 <div class="css-1smf7ma">Türk&nbsp;&nbsp;</div>
				 <?php }elseif(LANG_SET=='pt-pt'){?>
				 <div class="css-1smf7ma">Português&nbsp;&nbsp;</div>
				 <?php }elseif(LANG_SET=='es-es'){?>
				 <div class="css-1smf7ma">español&nbsp;&nbsp;</div>
				 <?php }?>

			 </div>
			 <ul class="sub" style="padding:0px;display:none;">
				 <!--<div class="order_navlist" style="min-width:160px;transform: translate(1161px, 64px);">-->
				 <div class="order_navlist" style="min-width:160px;top:50px;right: 10px;">
				 <li style="list-style:none;">
					 <a href="<?php echo U('?Lang=zh-cn');?>">
						 <div class="optionli">
							 <span style="color:#EAECEF;font-size:14px;">简体中文</span>
						 </div>
					 </a>
				 </li>
				 <li style="list-style:none;">
					 <a href="<?php echo U('?Lang=en-us');?>">
						 <div class="optionli">
							 <span style="color:#EAECEF;font-size:14px;">English</span>
						 </div>
					 </a>
				 </li>
				 <li style="list-style:none;">
					 <a href="<?php echo U('?Lang=fr-fr');?>">
						 <div class="optionli">
							 <span style="color:#EAECEF;font-size:14px;">Français</span>
						 </div>
					 </a>
				 </li>
				 <li style="list-style:none;">
					 <a href="<?php echo U('?Lang=de-de');?>">
						 <div class="optionli">
							 <span style="color:#EAECEF;font-size:14px;">Deutsch</span>
						 </div>
					 </a>
				 </li>
				 <li style="list-style:none;">
					 <a href="<?php echo U('?Lang=ja-jp');?>">
						 <div class="optionli">
							 <span style="color:#EAECEF;font-size:14px;">日本語</span>
						 </div>
					 </a>
				 </li>
				 <li style="list-style:none;">
					 <a href="<?php echo U('?Lang=it-it');?>">
						 <div class="optionli">
							 <span style="color:#EAECEF;font-size:14px;">Italiano</span>
						 </div>
					 </a>
				 </li>
				 <li style="list-style:none;">
					 <a href="<?php echo U('?Lang=ko-kr');?>">
						 <div class="optionli">
							 <span style="color:#EAECEF;font-size:14px;">한국어</span>
						 </div>
					 </a>
				 </li>
				 <li style="list-style:none;">
					 <a href="<?php echo U('?Lang=tr-tr');?>">
						 <div class="optionli">
							 <span style="color:#EAECEF;font-size:14px;">Türk</span>
						 </div>
					 </a>
				 </li>
				 <li style="list-style:none;">
					 <a href="<?php echo U('?Lang=pt-pt');?>">
						 <div class="optionli">
							 <span style="color:#EAECEF;font-size:14px;">Português</span>
						 </div>
					 </a>
				 </li>
				 <li style="list-style:none;">
					 <a href="<?php echo U('?Lang=es-es');?>">
						 <div class="optionli">
							 <span style="color:#EAECEF;font-size:14px;">español</span>
						 </div>
					 </a>
				 </li>
</div>
	     </li>
	 </div>
	 </ul>
</header> 
	            
	            <div class="css-ogtd7z">
	                <div class="css-jrzkh7">
	                    <div id="header_global_js_wxgy34nj" class="css-1aac2e"></div>
	                </div>
	           </div>
	           
	           <main class="css-1wr4jig">
	               <main class="css-xry4yv">
	                  
	                  <div class="css-1ay57iv">
	                      <div class="css-8hzjvg">
	                          <div class="css-1eklhqk">
	                              <div class="css-190uhut">
	                                  <div class="css-11y6cix"  onclick="goback();">
	                                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" class="css-146agw4"><path d="M16.414 18.586L15 20l-8-8 8-8 1.414 1.414L9.828 12l6.586 6.586z" fill="#76808F"></path>
	                                        </svg>
	                                        <div data-bn-type="text" class="css-1djsyd6"><?php echo L('数字货币提币');?></div>
	                                    </div>
	                              </div>
	                          </div>
	                      </div>
	                      
	                      <div class="css-1dihobw">
	                          <div class="css-1eklhqk">
	                              <div class="css-tu2ioc" style="width:60%;float:left;">
	                                  <div class="css-1ekpz1z">
	                                      <div class="czbox">
	                                          <div class="czbox_1">
	                                              <span class="fch f16"><?php echo L('选择币种');?></span>
	                                          </div>
	                                          <div class="czbox_2">
	                                              <div class="czbox_3">
	                                                  <span class="fch f12"><?php echo L('币种');?></span>
	                                              </div>
	                                              <div class="czbox_4">
	                                                  <span class="fch f14"><?php echo strtoupper($info['name']);?></span>
	                                              </div>
	                                          </div>
	                                      </div>
	                                      
	                                      <div class="czbox" style="margin-bottom:0px;">
	                                          <div class="czbox_1">
	                                              <span class="fch f16"><?php echo L('提币网络');?></span>
	                                          </div>
	                                          <div class="czbox_2">
	                                              <div  class="czbox_3">
	                                                  <span class="fch f12"><?php echo L('网络');?></span>
	                                              </div>
	                                              <div class="czbox_4">
	                                                  <span class="fch f14"><?php echo ($info["czline"]); ?></span>
	                                              </div>
	                                          </div>
	                                      </div>
	                                      
	                                      <div class="czbox"  style="margin-top:20px;">
	                                          <div class="czbox_1">
	                                              <span class="fch f16"><?php echo L('提币地址');?></span>
	                                          </div>
	                                          <div class="czbox_2">
	                                              <div  class="czbox_3">
	                                                  <span class="fch f12"><?php echo L('地址');?></span>
	                                              </div>
	                                              <div class="czbox_6">
	                                                  <input class="czbox_5" type="text" id="address" name="address" style="border: 1px solid #f5f5f5;padding:0px;padding-left: 15px;" />
	                                              </div>
	                                          </div>
	                                      </div>

	                                      
	                                      <div class="czbox" style="margin-top:20px;">
	                                          <div class="czbox_1">
	                                              <span class="fch f16"><?php echo L('提币数量');?></span>
	                                          </div>
	                                          <div class="czbox_2">
	                                              <div class="czbox_3">
	                                                  <span class="fch f12"><?php echo L('数量');?></span>
	                                                  <span class="f12 fcy"><?php echo L('可用');?>&nbsp;&nbsp;</span>
		                                              <span class="f12 fcy"><?php echo ($money); ?></span>
		                                              <span class="f12 fcy"><?php echo strtoupper($info['name']);?></span>
	                                              </div>
	                                              <div class="czbox_6" style="padding:0px;">
	                                                  <input class="czbox_5" type="text" id="tbnum" onblur="changenum();" name="tbnum" style="border: 1px solid #f5f5f5;padding:0px;padding-left: 15px;" autocomplete="off" step="1"  min="0" onkeyup="this.value= this.value.match(/\d+(\.\d{0,2})?/) ? this.value.match(/\d+(\.\d{0,2})?/)[0] : ''"  />
	                                                  <span class="f12 fcy"><?php echo L('实际到账');?></span>
	                                                  <span class="f12 fcy" id="tmoney">0.000000</span>
		                                              <span class="f12 fcy">&nbsp;&nbsp;<?php echo strtoupper($info['name']);?></span>
	                                              </div>
	                                          </div>
	                                      </div>
	                                      
	                                    <input type="hidden" id="usermoney" value="<?php echo ($money); ?>" />
		                                <input type="hidden" id="txminnum" value="<?php echo ($info["txminnum"]); ?>" />
		                                <input type="hidden" id="txsxf" value="<?php echo ($info["txsxf"]); ?>" />
		                                <input type="hidden" id="txsxf_n" value="<?php echo ($info["txsxf_n"]); ?>" />
		                                <input type="hidden" id="sxftype" value="<?php echo ($info["sxftype"]); ?>" />
		                                
		                                <input type="hidden" id="tbid" value="<?php echo ($info["id"]); ?>" />
		                                <input type="hidden" id="tbaddre" value="<?php echo ($adrinfo["addr"]); ?>" />

		                                <input type="hidden" id="flag" value="1" />
	                                      <div class="czbox">
	                                          <div class="czbox_2">
	                                              <div class="czbox_3"></div>
	                                              <div id="sumbtn" class="czbox_4" style="background: linear-gradient(to left,#eeb80d,#ffe35b);text-align:center;cursor:pointer;">
	                                                  <span class="fch f14"><?php echo L('提交');?></span>
	                                              </div>
	                                          </div>
	                                      </div>
	                                      
	                                  </div>
	                              </div>
	                              <div style="width:35%;height:600px;float:right;padding:10px;">
	                                  <div style="width:100%;height:60px;line-height:60px;">
	                                      <span><?php echo L('温馨提示');?></span>
	                                  </div>
	                                  <div style="width:100%;min-height:180px;padding:10px;">
	                                       <span class="f14" style="color:red;"><?php echo L('最小提币数量');?>：<?php echo strtoupper($info['txminnum']); echo strtoupper($info['name']);?>，<?php echo L('小于最小金额的提币将不会到账且无法退回');?></span>
	                                       <br />
	                                       <br />
		        <span class="f14 fch"><?php echo L('为保障资金安全，当您账户安全策略变更，密码修改，我们会对提币进行人工审核，请耐心等待工作人员电话或邮件联系.');?></span><br /><br />
		        <span class="f14 fch"><?php echo L('请务必确认电脑及浏览器安全，防止信息被篡改或泄露');?></span><br /><br />

	                                  </div>
	                              </div>
	                          </div>
	                      </div>
	                      
	                      
	                  </div>
	                  
	               </main>
	           </main>

	            <footer class="css-4qtnb6" style="height: 200px !important;">
    <div style="width:100%;height:150px;background: #181A20;padding:10px 48px;">
        <div style="width:100%;height:60px;">
            <div style="min-width:100px;height:60px;line-height:60px;margin-right:15px;float: left">
                <span
                        style="color: #fff;
                                font-size: 36px;
                                font-weight: 600;"
                ><?php echo get_config('webname');?></span>
            </div>
            <div style="min-width:100px;height:60px;line-height:60px;margin-right:15px;float: right" onclick="pop_box_show('privacy')">
                <a href="avascript:void(0)"  class="footer-box-span"  style="color:#848E9C;"><?php echo L('隐私政策申明');?></a>
            </div>
            <div style="min-width:100px;height:60px;line-height:60px;margin-right:15px;float: right" onclick="pop_box_show('service')">
                <a href="avascript:void(0)"  class="footer-box-span"  style="color:#848E9C;"><?php echo L('服务条款');?></a>
            </div>
            <div style="min-width:100px;height:60px;line-height:60px;margin-right:15px;float: right" onclick="pop_box_show('msb')">
                <a href="avascript:void(0)"  class="footer-box-span"  style="color:#848E9C;"><?php echo L('MSB证书');?></a>
            </div>
            <div style="min-width:100px;height:60px;line-height:60px;margin-right:15px;float: right" onclick="pop_box_show('about')">
                <a href="javascript:void(0)" class="footer-box-span" style="color:#848E9C;"><?php echo L('关于我们');?></a>
            </div>
        </div>
        <div style="width:100%;height:60px;line-height:90px;text-align:center;border-top:1px solid #848E9C;">
            <span style="color:#848E9C;">CopyRight © 2017 - 2022 <?php echo get_config('webname');?>. All Rights Reserved.</span>
        </div>
    </div>
</footer>


<div class="pop-box" id="pop-box" style="display: none" onclick="pop_box_hide()">
    <div class="pop-content">
        <div class="pop-content-desc" style="color: #2c3e50 !important;">

        </div>
    </div>
</div>

<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<script src="/Public/Home/static/js/layer/layer.js"></script>
<script>

    let text_obj = {
        "privacy" : "<div data-v-73cf4925=\"\"><p>(\"The Company\") understands the importance of including customers' personal information, and will comply with the provisions of the \"Personal Information Protection Act\" (\"Act\"), and endeavor to handle in an appropriate manner in accordance with the provisions of this privacy policy (\"Privacy Policy\") And protect personal information.</p>\n" +
            "<p>1. Definition</p>\n" +
            "<p>In the privacy policy, personal information refers to the “personal information” defined in the first paragraph of Article 2 of the Act, that is, information related to living individuals. This information can use the name, date of birth or other information contained in the information (including easy association To other information, so as to identify a specific individual) in the description to identify a specific individual.</p>\n" +
            "<p>2. Purpose of use</p>\n" +
            "<p>The company uses customers’ personal information for the following purposes:</p>\n" +
            "<p>Provide and improve the company's products or services;</p>\n" +
            "<p>Notify the company's products, services or activities;</p>\n" +
            "<p>Carry out marketing, survey or analysis to expand the scope of the company's products or services or improve their quality;</p>\n" +
            "<p>Provide maintenance or support for the company's services;</p>\n" +
            "<p>Notify the company of revisions to the terms of use, policies, etc. (\"Terms\") related to the services provided.</p>\n" +
            "<p>Deal with violations of the terms of the company's services;</p>\n" +
            "<p>Verify the account held by the user;</p>\n" +
            "<p>Verify the transfer to the user's account; or communicate in emergency situations.</p>\n" +
            "<p>Any other purpose related to the above purpose.</p>\n" +
            "<p>3. Change the purpose of use</p>\n" +
            "<p>The company can change the purpose of use of personal information so that the changed purpose of use can be reasonably regarded as related to the original purpose of use. After the purpose of use is changed, the company shall notify the user or publicly announce the changed purpose of use.</p>\n" +
            "<p>4. Restrict use</p>\n" +
            "<p>Without the consent of the relevant customer, the company shall not use personal information beyond the scope necessary for the realization of the purpose of use, unless permitted by the Act or other laws or regulations; however, this provision does not apply to the following situations:</p>\n" +
            "<p>Use personal information in accordance with laws and regulations;</p>\n" +
            "<p>The use of personal information is necessary to protect personal life, body or property, and it is difficult to obtain the consent of relevant customers;</p>\n" +
            "<p>The use of personal information is necessary to improve public health or promote the physical and mental health of children, and it is difficult to obtain the consent of relevant customers;</p>\n" +
            "<p>Or the use of personal information is necessary for the national government, local government, or individuals or entities entrusted to perform affairs prescribed by laws and regulations, and obtaining the consent of relevant customers may hinder the execution of related affairs.</p>\n" +
            "<p>5. Appropriate collection</p>\n" +
            "<p>The company may use appropriate means to collect personal information, but will not use deception or other improper means to collect personal information.</p>\n" +
            "<p>6. Security Control</p>\n" +
            "<p>The company fully and appropriately supervises its employees to ensure safe control of personal information to deal with the risk of loss, destruction, tampering or leakage.</p>\n" +
            "<p>7. If the customer requests the company to correct, add or delete personal information on the grounds that the personal information is contrary to the facts in accordance with the provisions of the Act, the company should first confirm that the request was made by the person in charge, and then immediately make use of the purpose of use Carry out necessary investigations within the necessary scope, and then correct according to the investigation results, add or delete personal information, and notify the customer of the relevant situation (the company decides not to perform the correction, and the company shall notify the customer of the relevant situation when adding or deleting); but According to the provisions of the Act or other laws and regulations, the company is not obliged to perform corrections. When adding or deleting, the provisions do not apply.</p>\n" +
            "<p>8. Forbidden</p>\n" +
            "<p>If the customer requests the company to stop using or delete personal information on the grounds that the company’s processing of personal information exceeds the previously announced purpose of use or obtains personal information by deception or other improper means in accordance with the provisions of the Act, and the investigation proves that If the request is reasonable, the company should first confirm that the request was made by the person in charge, and then immediately stop using or delete personal information and notify the customer of the relevant situation; however, the company is not obliged to stop according to the Act or other laws and regulations. This rule does not apply when using or deleting personal information.</p>\n" +
            "<p>9. Use information recording procedures and other technologies</p>\n" +
            "<p>The services provided by the company may use information recording procedures or similar technologies. These technologies help the company understand the use of the company's services, etc. and continue to improve services. When the user wants to disable the information recording program, the user can change the settings of the web browser to disable the information recording program. Please note that after the information logging program is disabled, users will no longer be able to use some parts of the service.</p></div>",
        "service" : "<div data-v-73cf4925=\"\"><p>The <?php echo get_config('webname');?> Global user agreement is the relevant rights and obligations stipulated by the user and the <?php echo get_config('webname');?> Global platform for various services, and is contractual.</p>\n" +
            "<p>By registering and using this website, the user means that he accepts and agrees to all the conditions and terms of the \"User Agreement\". Both <?php echo get_config('webname');?> Global and the user have carefully read all the terms in this \"User Agreement\" and the legal statements and operations issued by <?php echo get_config('webname');?> Global The content of the rules, this agreement and the aforementioned terms of service, legal statements and operating rules have been known, understood and accepted, and agreed to use them as the basis for determining the rights and obligations of both parties.</p>\n" +
            "<p>The <?php echo get_config('webname');?> Global \"legal statement\" is an essential part of this agreement. When the user accepts this agreement, it shall be deemed to have accepted the entire content of the <?php echo get_config('webname');?> Global \"legal statement\". The content of this agreement includes the body of this agreement and the published or Various rules, statements, and instructions that may be released in the future. All rules, statements, and instructions are an integral part of the agreement and have the same legal effect as the body of the agreement.</p>\n" +
            "<p>1. User service</p>\n" +
            "<p>1.1 <?php echo get_config('webname');?> Global provides online trading platform services for users to conduct encrypted digital currency transactions through the platform. <?php echo get_config('webname');?> Global does not participate in the buying and selling of any digital currency itself as a buyer or seller.</p>\n" +
            "<p>1.2 Users have the right to browse real-time digital currency market quotations and transaction information on <?php echo get_config('webname');?> Global, and have the right to submit digital currency transaction instructions and complete digital currency transactions through the <?php echo get_config('webname');?> Global platform.</p>\n" +
            "<p>1.3 Users have the right to view their information under the platform member account in <?php echo get_config('webname');?> Global, and have the right to use the functions provided by <?php echo get_config('webname');?> Global to operate.</p>\n" +
            "<p>1.4 Users have the right to participate in website activities organized by the platform in accordance with the activity rules published by <?php echo get_config('webname');?> Global.</p>\n" +
            "<p>1.5 Users should abide by laws, regulations, regulatory documents and policy requirements, ensure the legitimacy of all funds and digital currency sources in the account, and must not engage in illegal or other damage to the platform or the third party in <?php echo get_config('webname');?> Global or use <?php echo get_config('webname');?> Global services. The activities of tripartite rights, such as sending or receiving any information that violates laws, regulations, public order and good customs, or infringes on the rights and interests of others, sending or receiving pyramid schemes or other harmful information or speech, using or forging <?php echo get_config('webname');?> Global electronics without the authorization of <?php echo get_config('webname');?> Global Email header information, etc.</p>\n" +
            "<p>1.6 Users should abide by laws and regulations, and should properly use and keep their <?php echo get_config('webname');?> Global platform account and password, fund transaction password, mobile phone number bound to the registration time, and the security of the mobile phone verification code received by the mobile phone. The user is fully responsible for any operations and consequences performed using his platform account and password, capital password, and mobile phone verification code. When the user discovers that the <?php echo get_config('webname');?> Global platform account, password or fund password, verification code is used by a third party without its authorization, or there are other account security issues, the <?php echo get_config('webname');?> Global platform will be notified immediately and effectively, and the platform will be required to suspend the service <?php echo get_config('webname');?> Global of the platform account. The <?php echo get_config('webname');?> Global platform has the right to take action on the user’s request within a reasonable time, but it does not assume any responsibility for the losses that the user has suffered before the <?php echo get_config('webname');?> Global platform takes action. The user shall not give, borrow, rent, transfer or otherwise dispose of the <?php echo get_config('webname');?> Global platform account to others without the consent of the <?php echo get_config('webname');?> Global platform.</p>\n" +
            "<p>1.7 The user shall abide by the user agreement and other terms of service and operating rules published and updated by the <?php echo get_config('webname');?> Global platform from time to time.</p>\n" +
            "<p>Second, the rights and obligations of users</p>\n" +
            "<p>2.1 The user has the right to accept the digital currency trading platform services provided by <?php echo get_config('webname');?> Global in accordance with this agreement.</p>\n" +
            "<p>2.2 The user has the right to terminate the use of <?php echo get_config('webname');?> Global platform services at any time.</p>\n" +
            "<p>2.3 Users have the right to withdraw the balance of funds in <?php echo get_config('webname');?> Global at any time, but they need to pay the corresponding withdrawal fees to the <?php echo get_config('webname');?> Global platform.</p>\n" +
            "<p>2.4 The user is responsible for the authenticity, validity and security of the personal information provided during registration.</p>\n" +
            "<p>2.5 When users conduct digital currency transactions on the <?php echo get_config('webname');?> Global platform, they must not maliciously interfere with the normal conduct of digital currency transactions and disrupt the order of transactions.</p>\n" +
            "<p>2.6 Users must not interfere with the normal operation of the <?php echo get_config('webname');?> Global platform or interfere with other users' use of the <?php echo get_config('webname');?> Global platform services by any technical means or other means.</p>\n" +
            "<p>2.7 If users have litigation with other users due to online transactions, they must not request the <?php echo get_config('webname');?> Global platform to provide relevant data through judicial or administrative channels.</p>\n" +
            "<p>2.8 Users shall not maliciously slander the reputation of the <?php echo get_config('webname');?> Global platform by fabricating facts or other means.</p>\n" +
            "<p>Third, the rights and obligations of the <?php echo get_config('webname');?> Global platform</p>\n" +
            "<p>3.1 If the user does not have the registration qualifications stipulated in this agreement, the <?php echo get_config('webname');?> Global platform has the right to refuse the user to register, and the registered user has the right to cancel his <?php echo get_config('webname');?> Global platform member account. The <?php echo get_config('webname');?> Global platform suffers losses due to this. The right to claim compensation from the aforementioned users or their legal representatives. At the same time, the <?php echo get_config('webname');?> Global platform reserves the right to decide whether to accept user registration under any other circumstances.</p>\n" +
            "<p>When the <?php echo get_config('webname');?> Global platform finds that the account user is not the initial registrant of the account, it has the right to suspend the use of the account.</p>\n" +
            "<p>3.2 When the <?php echo get_config('webname');?> Global platform reasonably suspects that the information provided by the user is incorrect, false, invalid or incomplete through technical testing, manual sampling and other testing methods, it has the right to notify the user to correct, update the information or suspend, and terminate the provision of the <?php echo get_config('webname');?> Global platform. service.</p>\n" +
            "<p>3.3 The <?php echo get_config('webname');?> Global platform has the right to correct any information displayed on the <?php echo get_config('webname');?> Global platform when there are obvious errors.</p>\n" +
            "<p>The platform reserves the right to modify, suspend or terminate the <?php echo get_config('webname');?> Global platform services at any time. The <?php echo get_config('webname');?> Global platform exercises the right to modify or suspend the services without prior notice to the user. If the <?php echo get_config('webname');?> Global platform terminates one or more services of the <?php echo get_config('webname');?> Global platform, The termination will take effect on the day when the <?php echo get_config('webname');?> Global platform publishes the termination announcement on the website.</p>\n" +
            "<p>3.4 The <?php echo get_config('webname');?> Global platform shall adopt necessary technical means and management measures to ensure the normal operation of the <?php echo get_config('webname');?> Global platform, and provide necessary and reliable trading environment and transaction services to maintain the order of digital currency transactions.</p>\n" +
            "<p>3.5 If the user has not used the <?php echo get_config('webname');?> Global platform member account and password to log in to the <?php echo get_config('webname');?> Global platform for three consecutive years, the <?php echo get_config('webname');?> Global platform has the right to cancel the user's <?php echo get_config('webname');?> Global platform account. After the account is cancelled, the <?php echo get_config('webname');?> Global platform has the right to open the corresponding member name to other users for registration.</p>\n" +
            "<p>3.7 The <?php echo get_config('webname');?> Global platform guarantees the safety of users' RMB funds and digital currency custody by strengthening technical investment and improving security precautions. It is obliged to notify users in advance when there are foreseeable security risks in user funds.</p>\n" +
            "<p>3.8 The <?php echo get_config('webname');?> Global platform has the right to delete all kinds of content and information on the <?php echo get_config('webname');?> Global platform website that do not comply with national laws and regulations, regulatory documents or reports stipulated by the <?php echo get_config('webname');?> Global platform website. The <?php echo get_config('webname');?> Global platform does not need to notify in advance to exercise this right user.</p>\n" +
            "<p>Four, special statement</p>\n" +
            "<p>To the extent permitted by law, under any circumstances, the <?php echo get_config('webname');?> Global platform is protected against maintenance of information network equipment, information network connection failures, computer, communications or other system failures, power failures, strikes, labor disputes, riots, and uprisings. , Riots, insufficient productivity or production data, fires, floods, storms, explosions, wars, government actions, orders from judicial administrative organs, other force majeure or third-party inactions caused by inability to service or delayed services, and users suffered as a result The loss is not liable.</p>\n" +
            "<p>Five, customer service</p>\n" +
            "<p>The <?php echo get_config('webname');?> Global platform has established a professional customer service team and established a complete customer service system to ensure the smooth flow of user questions and complaint channels in terms of technology, personnel and systems, and provide users with timely troubleshooting and complaint feedback.</p>\n" +
            "<p>Six, intellectual property</p>\n" +
            "<p>6.1 All intellectual achievements contained in the <?php echo get_config('webname');?> Global platform include but are not limited to website logos, databases, website design, text and graphics, software, photos, videos, music, sounds and the foregoing combinations, software compilation, related source codes and software applications The intellectual property rights of programs and scripts are owned by the <?php echo get_config('webname');?> Global platform. Users shall not copy, change, copy, send or use any of the aforementioned materials or content for commercial purposes.</p>\n" +
            "<p>6.2 All rights (including but not limited to goodwill and trademarks, logos) contained in the name of the <?php echo get_config('webname');?> Global platform belong to the <?php echo get_config('webname');?> Global platform.</p>\n" +
            "<p>6.3 The user's acceptance of this agreement shall be deemed as the user's initiative to have the copyright of any form of information published on the <?php echo get_config('webname');?> Global platform, including but not limited to: reproduction rights, distribution rights, rental rights, exhibition rights, performance rights, projection rights, broadcasting rights Rights, information network communication rights, filming rights, adaptation rights, translation rights, compilation rights and other transferable rights attributable to the copyright owner are exclusively transferred to the <?php echo get_config('webname');?> Global platform for free, and the <?php echo get_config('webname');?> Global platform has the right to infringe on any subject Individually file a lawsuit and obtain full compensation. This agreement is a written agreement stipulated in Article 25 of the \"United Nations Copyright Law\", and its validity is applicable to the content of any copyright law-protected works published by users on the <?php echo get_config('webname');?> Global platform, regardless of the formation of the content Before signing this agreement or after signing this agreement.</p>\n" +
            "<p>6.4 Users shall not illegally use the <?php echo get_config('webname');?> Global platform or the intellectual property rights of others when using the <?php echo get_config('webname');?> Global platform services.</p>\n" +
            "<p>Seven, privacy policy</p>\n" +
            "<p>7.1 When a user registers an <?php echo get_config('webname');?> Global platform account or payment account, the user provides personal registration information according to the requirements of the <?php echo get_config('webname');?> Global platform, including but not limited to identity card information.</p>\n" +
            "<p>7.2 When the user uses the <?php echo get_config('webname');?> Global platform service or visits the <?php echo get_config('webname');?> Global platform webpage, the <?php echo get_config('webname');?> Global platform automatically receives and records the server value on the user’s browser, including but not limited to data such as IP address and user requirements for access Web records of.</p>\n" +
            "<p>7.3 Relevant data collected by the <?php echo get_config('webname');?> Global platform of users' transactions on the <?php echo get_config('webname');?> Global platform, including but not limited to records of bids and purchases.</p>\n" +
            "<p>7.4 The personal information of other users obtained by the <?php echo get_config('webname');?> Global platform through legal means.</p>\n" +
            "<p>7.5 The <?php echo get_config('webname');?> Global platform will not sell or lend the user's personal information to anyone unless the user's permission is obtained in advance. The <?php echo get_config('webname');?> Global platform does not allow any third party to collect, edit, sell or disseminate the user's personal information by any means.</p>\n" +
            "<p>7.6 The <?php echo get_config('webname');?> Global platform keeps the obtained customer identity data and transaction information confidential, and must not provide customer identity data and transaction information to any unit or individual, unless otherwise provided by laws and regulations.</p>\n" +
            "<p>Eight, anti-money laundering</p>\n" +
            "<p>8.1 The <?php echo get_config('webname');?> Global platform complies with and implements the provisions of the \"Anti-Money Laundering Law of the People's Republic of China\" to identify users, maintain a system for customer identity data and transaction history records, as well as a system for large and suspicious transaction reports.</p>\n" +
            "<p>8.2 When users register and modify their real-name information, they should provide and upload necessary evidence such as a copy of their ID card. The <?php echo get_config('webname');?> Global platform will identify and compare the ID card information provided by the user. The <?php echo get_config('webname');?> Global platform has reasonable grounds to suspect that when a user registers with a false identity, it has the right to refuse to register or cancel the registered account.</p>\n" +
            "<p>8.3 The <?php echo get_config('webname');?> Global platform refers to the provisions of the “Measures for the Administration of Large-Value Transactions and Suspicious Transaction Reports of Financial Institutions” to keep historical records of large-value transactions and transactions suspected of money laundering. When regulatory agencies require records of large-value transactions and suspicious transactions, Provide to regulatory agencies.</p>\n" +
            "<p>8.4 The <?php echo get_config('webname');?> Global platform saves user identity information, large-value transactions, and historical records of suspicious transactions, assists in accordance with the law, cooperates with judicial and administrative law enforcement agencies in combating money laundering activities, and assists judicial agencies, customs, taxation and other departments to inquire in accordance with laws and regulations , Freezing and deducting customer deposits.</p>\n" +
            "<p>8.5 According to the national anti-money laundering policy and the protection of customer assets, the name of the remitter must be the same as the real-name certified name.</p>\n" +
            "<p>Nine. Liability for breach of contract</p>\n" +
            "<p>9.1 The violation of the <?php echo get_config('webname');?> Global platform or the user of this agreement constitutes a breach of contract, and the breaching party shall be liable for breach of contract to the observant party.</p>\n" +
            "<p>9.2 If the <?php echo get_config('webname');?> Global platform causes losses to the <?php echo get_config('webname');?> Global platform due to untrue, incomplete or inaccurate information provided by the user, the <?php echo get_config('webname');?> Global platform has the right to request the user to compensate the <?php echo get_config('webname');?> Global platform for losses.</p>\n" +
            "<p>9.3 If a user engages in illegal activities on the <?php echo get_config('webname');?> Global platform or using the <?php echo get_config('webname');?> Global platform services due to violation of laws and regulations or the provisions of this agreement, the <?php echo get_config('webname');?> Global platform has the right to immediately terminate the continued provision of <?php echo get_config('webname');?> Global platform services to them, and cancel them Account and demand compensation for the losses caused to the <?php echo get_config('webname');?> Global platform.</p>\n" +
            "<p>9.4 If User interferes with the operation of <?php echo get_config('webname');?> Global Platform by technical means or interferes with the use of <?php echo get_config('webname');?> Global Platform by other Users, <?php echo get_config('webname');?> Global shall have the right to immediately cancel the account of the User on <?php echo get_config('webname');?> Global Platform and claim compensation for the losses caused to <?php echo get_config('webname');?> Global.</p>\n" +
            "<p>9.5 If users maliciously slander the reputation of the <?php echo get_config('webname');?> Global platform by fabricating facts, etc., the <?php echo get_config('webname');?> Global platform has the right to request the user to publicly apologize to the <?php echo get_config('webname');?> Global platform, compensate for the losses caused to the <?php echo get_config('webname');?> Global platform, and have the right to terminate it Provide <?php echo get_config('webname');?> Global platform services.</p>\n" +
            "<p>Ten. Entry into force and interpretation of the agreement</p>\n" +
            "<p>This agreement takes effect when the user clicks on the <?php echo get_config('webname');?> Global platform registration page to agree to register and complete the registration process, and obtains the <?php echo get_config('webname');?> Global platform account and password, and is binding on the <?php echo get_config('webname');?> Global platform and users.</p>\n" +
            "<p>Eleven. Modification and termination of the agreement</p>\n" +
            "<p>11.1 Changes to the agreement: the <?php echo get_config('webname');?> Global platform has the right to change the content of this agreement or other terms of service and operating rules published by the <?php echo get_config('webname');?> Global platform at any time. When the change is made, the <?php echo get_config('webname');?> Global platform will publish an announcement in a prominent place on the <?php echo get_config('webname');?> Global platform. , The change takes effect when the announcement is released. If the user continues to use the services provided by the <?php echo get_config('webname');?> Global platform, it is deemed that the user agrees to the content changes. If the user does not agree with the content after the change, the user has the right to cancel the <?php echo get_config('webname');?> Global platform account and stop Use <?php echo get_config('webname');?> Global platform services.</p>\n" +
            "<p>11.2 Termination of the agreement</p>\n" +
            "<p>11.2.1 The <?php echo get_config('webname');?> Global platform has the right to cancel the user's <?php echo get_config('webname');?> Global platform account in accordance with this agreement, and this agreement terminates on the date of account cancellation.</p>\n" +
            "<p>11.2.2 The <?php echo get_config('webname');?> Global platform has the right to terminate all <?php echo get_config('webname');?> Global platform services in accordance with this agreement, and this agreement terminates on the day when all <?php echo get_config('webname');?> Global platform services are terminated.</p>\n" +
            "<p>11.2.3 After the termination of this agreement, the user has no right to require the <?php echo get_config('webname');?> Global platform to continue to provide it with any services or perform any other obligations, including but not limited to requiring the <?php echo get_config('webname');?> Global platform to retain or disclose its original <?php echo get_config('webname');?> Global platform to the user Any information in the account, forward any information that has not been read or sent to the user or a third party.</p>\n" +
            "<p>11.2.4 The termination of this agreement does not affect the observant party to the breaching party to pursue liability for breach of contract.</p>\n" +
            "<p>&nbsp;</p></div>",
        "msb" : '<img src="/Public/Home/static/imgs/1.jpeg" style="width: 45%">\n' +
            '            <img src="/Public/Home/static/imgs/2.jpeg" style="width: 45%">',
        "about" : "<div data-v-73cf4925=\"\"><p>&nbsp; &nbsp; &nbsp; <?php echo get_config('webname');?> cryptocurrency exchange is headquartered in Singapore. In addition, there are three operation centers in the United States, South Korea, and Hong Kong. The scope of services is vast and the market radiates all over the world.</p>\n" +
            "<p>&nbsp; &nbsp; &nbsp; <?php echo get_config('webname');?> has a professional, efficient and experienced blockchain technology and operation team with decades of experience in Internet development and services. A group of Internet experts with unique insights and foresight are committed to providing a safe, convenient, stable and low transaction cost platform for global cryptocurrency contract trading users. The main members of the team come from well-known companies such as Google, Amazon and Alibaba.</p>\n" +
            "<p>&nbsp;</p>\n" +
            "<p><strong>A. Strength</strong></p>\n" +
            "<p><?php echo get_config('webname');?> is committed to building a safe and reliable cryptocurrency trading platform. The team has decades of experience in financial risk control. The core members graduated from prestigious universities such as Harvard University, Stanford University, University of California, Berkeley, Hong Kong University, Seoul University and Tsinghua University. <?php echo get_config('webname');?> is headquartered in Singapore and holds dual financial licenses. The platform is stable for a long time and venture capital is guaranteed.</p>\n" +
            "<p><strong>B, focus</strong></p>\n" +
            "<p><?php echo get_config('webname');?> focuses on cryptocurrency intraday trading, contract trading, second contract trading, ICO and cloud mining. The exchange provides systematic technology and service solutions for contract transactions, and selects the world's mainstream cryptocurrencies.</p>\n" +
            "<p><strong>C, smooth</strong></p>\n" +
            "<p>The exchange system fully optimizes the user experience, the load multi-point shunt technology maximizes the smoothness of the system and provides multi-level servers to guarantee the transaction speed! The trading system experience satisfaction is benchmarked against the world's top trading system.</p>\n" +
            "<p><strong>D, safety</strong></p>\n" +
            "<p><?php echo get_config('webname');?>'s financial-level security protects user assets, digital asset storage is intelligently separated from hot and cold, ERC20 digital wallets, and account encryption technologies are fully applied.</p>\n" +
            "<p><strong>E, service</strong></p>\n" +
            "<p><?php echo get_config('webname');?> has an independent and complete user service system, provides the most complete and convenient management system support, 7*24h quick response, and truly creates a fair, just and open data trading market</p>\n" +
            "<p><strong>F, platform advantages</strong></p>\n" +
            "<p>1. Features two - way trading, leveraged contracts, second contracts.</p>\n" +
            "<p>2. The interface is simple and clear, easy to operate.</p>\n" +
            "<p>3. The deposit and withdrawal is convenient and fast, and can be transferred in major exchanges and wallets.</p>\n" +
            "<p>4.ICO(Initial <?php echo get_config('webname');?> Offering): Use blockchain to combine rights and cryptocurrency to finance projects to develop, maintain, and exchange related products or services</p></div>"
    }

    function pop_box_show(type) {
        let pop_text = '';
        let pop_title = '';
        if (type == 'privacy') {
            pop_text = text_obj.privacy;
            pop_title = 'Privacy';
        }
        if (type == 'service') {
            pop_text = text_obj.service;
            pop_title = 'Service';
        }
        if (type == 'msb') {
            pop_text = text_obj.msb;
            pop_title = 'Msb';
        }
        if (type == 'about') {
            pop_text = text_obj.about;
            pop_title = 'About';
        }
        // $('#pop-box').show();
        $('.pop-content-desc').html(pop_text)
        layer.open({
            type: 1,
            area: ['80%', '80vh'],
            shadeClose: true,
            title: pop_title,
            content: $('#pop-box') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        });
    }
    
    function pop_box_hide() {
        $('#pop-box').hide();
    }

    $('.pop-content').on('click',function(event){
        event.stopPropagation();
        console.log('btn2被点击了')
    })

    $('.footer-box-span').hover(
        function () {
            $(this).css('color', '#fff');
        },
        function () {
            $(this).css('color', '#848E9C');
        }
    )


</script>
	        </div>   
	    </div>
	    
	</body>
	<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="/Public/Home/static/js/layer/layer.js" ></script>
    <script type="text/javascript" src="/Public/Home/static/js/jquery.SuperSlide.2.1.1.js" ></script>
    <script type="text/javascript" src="/Public/layui/layui.js"></script>
    <script type="text/javascript">
        function changenum(){
            var tbnum = parseFloat($("#tbnum").val());
            if(tbnum <= 0){
                layer.msg("<?php echo L('请输入正确的数量');?>");return false;
            }
            var txminnum = parseFloat($("#txminnum").val());
            if(tbnum < txminnum){
                layer.msg("<?php echo L('不能低于最小提币值');?>");return false;
            }
            
            var sxftype = $("#sxftype").val();
            var txsxf = $("#txsxf").val();
            var txsxf_n = $("#txsxf_n").val();
            if(sxftype == 1){
                var tmoney = tbnum - tbnum * txsxf / 100;
            }else if(sxftype == 2){
                var tmoney = tbnum - txsxf_n;
            }
            
            $("#tmoney").text(tmoney);
      
        }
    </script>
    <script type="text/javascript">
        $("#sumbtn").click(function(){
            var flag = $("#flag").val();
            if(flag == 2){
                return false;
            }
            var address = $("#address").val();
            var num = parseFloat($("#tbnum").val());
            var txminnum = parseFloat($("#txminnum").val());
            var id = $("#tbid").val();
            if(id <= 0){
                layer.msg("<?php echo L('参数错误');?>");return false;
            }
            if(address == '' || address == null){
                layer.msg("<?php echo L('请输入提币地址');?>");return false;
            }
            if(num <= 0){
                layer.msg("<?php echo L('请输入正确的数量');?>");return false;
            }
            if(num < txminnum){
                layer.msg("<?php echo L('不能低于最小提币值');?>");return false;
            }
            $("#flag").val(2);
            $.post("<?php echo U('Finance/tbhandle');?>",
            {'address':address,'num':num,'id':id},
            function(data){
                if(data.code == 1){
                    layer.msg(data.info);
                    setTimeout(function(){
                        window.location.href = "<?php echo U('Finance/index');?>";
                    },2000);
                }else{
                    layer.msg(data.info);return false;
                }
            });
        });
    </script>
    <script type="text/javascript">
        function goback(){
            self.location=document.referrer;
        }
    </script>
    <script type="text/javascript">
		$("#nav").slide({ 
			type:"menu",// 效果类型，针对菜单/导航而引入的参数（默认slide）
			titCell:".nLi", //鼠标触发对象
			targetCell:".sub", //titCell里面包含的要显示/消失的对象
			effect:"slideDown", //targetCell下拉效果
			delayTime:300 , //效果时间
			triggerTime:0, //鼠标延迟触发时间（默认150）
			returnDefault:true //鼠标移走后返回默认状态，例如默认频道是“预告片”，鼠标移走后会返回“预告片”（默认false）
		});
	</script>

    
</html>