<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html   >
<head>
    <meta charset="UTF-8">
    <title><?php echo ($webname); ?></title>
    <link rel="stylesheet" href="/Public/Home/login/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="/Public/Home/static/css/base.css" />
    <link rel="stylesheet" href="/Public/Home/static/css/style.css">
    <style>
        body {
            padding: 0px;
        }
    </style>
</head>
<body>

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

<!-- partial:index.partial.html -->
<div class="starfield">
    <div class="static"></div>
    <div class="moving-1"></div>
    <div class="moving-2"></div>
    <div class="moving-3"></div>
</div>

<!-- partial -->

<!-- partial:index.partial.html -->
<div class="container">



    <!-- code here -->
    <div class="card">
        <form class="card-form" onclick="return false">
            <div class="margin-topbox-px-10">
                <div data-bn-type="text" class="css-1g5tc38  tcc fch f36 fw"><?php echo L('欢迎来到');?><span class="floginbr"> <?php echo get_config('webname');?></span></div>
            </div>
            <div class="input">
                <input type="text" class="input-field" name="email" id="email" value="" required/>
                <label class="input-label"><?php echo L('账号');?></label>
            </div>
            <div class="input">
                <input type="password" class="input-field"  name="lpwd" id="lpwd" value="" required/>
                <label class="input-label"><?php echo L('密码');?></label>
            </div>
            <div class="input">
                <input type="text" class="input-field"  name="vcode" id="vcode" value="" required style="width: 60%" />
                <label class="input-label"><?php echo L('验证码');?></label>
                <div class="action-button login-send-button">
                    <img style="width:100%;height:50px;border-radius:5px;" src="<?php echo U('Verify/code');?>" onclick="this.src=this.src+'?t='+Math.random()" title="<?php echo L('换一张');?>">
                </div>
            </div>
            <div class="action">
                <button class="action-button" onclick="uplogin()"><?php echo L('登录');?></button>
            </div>
        </form>
        <div class="login-reg tcr">
            <a href="<?php echo U('Login/findpwd');?>"  class="css-s84t59 fbaseblue" ><?php echo L('忘记密码');?>？</a>
            <a href="<?php echo U('Login/register');?>"  class="css-utqtyo fbaseblue"><?php echo L('立即注册');?></a>
        </div>
    </div>
</div>
<!-- partial -->

</body>


<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/Public/Home/static/js/layer/layer.js" ></script>
<script type="text/javascript" src="/Public/Home/static/js/jquery.SuperSlide.2.1.1.js" ></script>

<script type="text/javascript">
    function uplogin(){
        var email = $("#email").val();
        var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        if(email=='' || email == null){
            layer.msg("<?php echo L('请输入账号');?>");return false;
        }
        // if(!reg.test(email)){
        //     layer.msg("<?php echo L('邮箱格式不正确');?>");return false;
        // }
        var vcode = $("#vcode").val();
        if(vcode == ''){
            layer.msg("<?php echo L('请输入图形验证码');?>");return false;
        }
        var lpwd = $("#lpwd").val();
        if(lpwd == ''){
            layer.msg("<?php echo L('请输入密码');?>");return false;
        }

        $.post("<?php echo U('Login/loginsubmit');?>",
            {'email' : email, 'vcode' : vcode, 'lpwd' : lpwd},
            function(data){
                if(data.code){
                    layer.msg(data.info);
                    setTimeout(function(){
                        window.location.href="<?php echo U('Index/index');?>";
                    },2000);
                }else{
                    layer.msg(data.info);return false;
                }
            });

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