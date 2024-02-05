<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>后台 | 管理中心 - ADMIN EX</title>
	<!-- Loading Bootstrap -->
	<link rel="stylesheet" type="text/css" href="/Public/Admin/css/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/Public/Admin/css/base.css" media="all">
	<link rel="stylesheet" type="text/css" href="/Public/Admin/css/common.css" media="all">
	<link rel="stylesheet" type="text/css" href="/Public/Admin/css/module.css">
	<link rel="stylesheet" type="text/css" href="/Public/Admin/css/style.css" media="all">
	<link rel="stylesheet" type="text/css" href="/Public/Admin/css/default_color.css" media="all">
	<script type="text/javascript" src="/Public/Admin/js/jquery.min.js"></script>
	<script type="text/javascript" src="/Public/layer/layer.js"></script>
	<link rel="stylesheet" type="text/css" href="/Public/Admin/css/flat-ui.css">
	<script src="/Public/Admin/js/flat-ui.min.js"></script>
	<script src="/Public/Admin/js/application.js"></script>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="navbar-header">
		<a class="navbar-brand" style="width:200px;text-align:center;background-color:#21202a;" href="<?php echo U('Index/index');?>">
			<img src="/Public/Admin/ecshe_img/alogn.png" width="40" />
		</a>
	</div>
	<div class="navbar-collapse collapse">
		<ul class="nav navbar-nav">
			<!-- 主导航 -->
			<?php if(is_array($__MENU__["main"])): $i = 0; $__LIST__ = $__MENU__["main"];if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$menu): $mod = ($i % 2 );++$i;?><li <?php if(($menu["class"]) == "current"): ?>class="active"<?php endif; ?> > 
					<a href="<?php echo (U($menu["url"])); ?>">
						<?php echo ($menu["title"]); ?> 
					</a>
				</li><?php endforeach; endif; else: echo "" ;endif; ?>
		</ul>
		<ul class="nav navbar-nav navbar-rights" style="margin-right:10px;">
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					 <?php echo session('admin_username');?><b class="caret"></b>
				</a>
				<ul class="dropdown-menu">
					<li>
						<a href="<?php echo U('User/setpwd');?>">
							<span class="glyphicon glyphicon-wrench" aria-hidden="true"></span> 修改密码 
						</a>
					</li>
					<li class="center">
						<a href="javascript:void(0);" onclick="lockscreen()">
							<span class="glyphicon glyphicon-lock" aria-hidden="true"></span> 锁屏休息 
						</a>
					</li>
					<li class="dividers"></li>
					<li>
						<a href="<?php echo U('Login/loginout');?>">
							<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> 退出后台 
						</a>
					</li>
				</ul>
			</li>
			<li>
				<a href="<?php echo U('Tools/delcache');?>" class="dropdown-toggle" title="清除缓存">
					<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
				</a>
			</li>
			<li>
				<a class="dropdown-toggle" title="打开前台" href="/" target="_blank">
					<span class="glyphicon glyphicon-share" aria-hidden="true"></span>
				</a>
			</li>
		</ul>
	</div>
</div>
<!-- 边栏 -->
<div class="sidebar">
	<!-- 子导航 -->
	
		<div id="subnav" class="subnav" style="max-height: 94%;overflow-x: hidden;overflow-y: auto;">
			<?php if(!empty($_extra_menu)): ?> <?php echo extra_menu($_extra_menu,$__MENU__); endif; ?>
			<?php if(is_array($__MENU__["child"])): $i = 0; $__LIST__ = $__MENU__["child"];if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$sub_menu): $mod = ($i % 2 );++$i;?><!-- 子导航 -->
				<?php if(!empty($sub_menu)): if(!empty($key)): ?><h3><i class="icon icon-unfold"></i><?php echo ($key); ?></h3><?php endif; ?>
					<ul class="side-sub-menu">
						<?php if(is_array($sub_menu)): $i = 0; $__LIST__ = $sub_menu;if( count($__LIST__)==0 ) : echo "" ;else: foreach($__LIST__ as $key=>$menu): $mod = ($i % 2 );++$i;?><li>
								<a class="item" href="<?php echo (U($menu["url"])); ?>">
									<?php if(empty($menu["ico_name"])): ?><span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
										<?php else: ?>
										<span class="glyphicon glyphicon-<?php echo ($menu["ico_name"]); ?>" aria-hidden="true"></span><?php endif; ?>
									<?php echo ($menu["title"]); ?>
								</a>
							</li><?php endforeach; endif; else: echo "" ;endif; ?>
					</ul><?php endif; ?>
				<!-- /子导航 --><?php endforeach; endif; else: echo "" ;endif; ?>
		</div>
	
	<!-- /子导航 -->
</div>
<!-- /边栏 -->
<script type="text/javascript">
    $(function(){
        setInterval("tzfc()",8000);
    });
    
    function tzfc(){
        var st = 1;
        $.post("<?php echo U('Admin/Trade/gethyorder');?>",
        {'st':st},
        function(data){
            if(data.code == 1){
                 //  var mp3 = new Audio('/heyue.mp3');  // 创建音频对象
               // mp3.play(); 
                return false;
                layer.confirm('有新的合约订单', {
                  btn: ['知道了'] //按钮
                }, function(){
                    
                    $.post("<?php echo U('Admin/Trade/settzstatus');?>",
                    function(data){
                        if(data.code == 1){
                            window.location.reload();  
                        } 
                    });
                });
            }   
        });
    }
</script>



<?php if(($versionUp) == "1"): ?><script type="text/javascript" charset="utf-8">
		/**顶部警告栏*/
		var top_alert = $('#top-alerta');
		top_alert.find('.close').on('click', function () {
			top_alert.removeClass('block').slideUp(200);
			// content.animate({paddingTop:'-=55'},200);
		});
	</script><?php endif; ?>
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

	.css-xry4yv {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		min-height: 600px;
		flex: 1 1 0%;
		flex-direction: column;
	}

	.css-xry4yv {
		flex-direction: row;
	}

	.css-foka8b {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		box-shadow: rgb(0 0 0 / 8%) 0px 2px 4px, rgb(0 0 0 / 8%) 0px 0px 4px;
		position: relative;
		z-index: 1;
		flex-direction: column;
		width: 200px;
		background: #ffffff;
	}

	.css-160vccy {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		flex: 1 1 0%;
		background-color: rgb(250, 250, 250);
	}

	.css-z87e9z {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		text-decoration: none;
		color: rgb(201, 148, 0);
		border-left: 4px solid #00b897;
		height: 48px;
		background-color: rgb(245, 245, 245);
		font-weight: 500;
		display: flex;
		-webkit-box-align: center;
		align-items: center;
		-webkit-box-pack: justify;
		justify-content: space-between;
	}

	.css-10j588g {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		flex: 1 1 0%;
		height: 100%;
		-webkit-box-align: center;
		align-items: center;
	}

	.css-iizq59 {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		font-weight: 500;
		font-size: 14px;
		line-height: 20px;
		word-break: break-word;
		display: flex;
		flex: 1 1 0%;
		height: 100%;
		-webkit-box-align: center;
		align-items: center;
		color: rgb(33, 40, 51);
	}

	.css-14thuu2 {
		box-sizing: border-box;
		margin: 0px 8px;
		min-width: 0px;
		color: #00b897;
		font-size: 24px;
		fill: #00b897;
		width: 1em;
		flex-shrink: 0;
	}

	.css-6ijtmk {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		text-decoration: none;
		color: rgb(201, 148, 0);
		border-left: 4px solid transparent;
		height: 48px;
		display: flex;
		-webkit-box-align: center;
		align-items: center;
		-webkit-box-pack: justify;
		justify-content: space-between;
		background: #fff;
	}

	.css-hd27fe {
		box-sizing: border-box;
		margin: 0px 8px;
		min-width: 0px;
		color: rgb(132, 142, 156);
		font-size: 24px;
		fill: rgb(132, 142, 156);
		width: 1em;
		flex-shrink: 0;
	}

	.css-1n0484q {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		font-weight: 400;
		font-size: 14px;
		line-height: 20px;
		word-break: break-word;
		display: flex;
		flex: 1 1 0%;
		height: 100%;
		-webkit-box-align: center;
		align-items: center;
		color: rgb(33, 40, 51);
	}

	.css-146q23 {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		flex: 1 1 0%;
		background-color: rgb(255, 255, 255);
	}

	.css-jlbk6n {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		flex-wrap: wrap;
		-webkit-box-pack: justify;
		justify-content: space-between;
		padding: 8px;
	}

	.css-1e6doj4 {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		box-shadow: rgb(0 0 0 / 5%) 0px 0px 4px;
		flex: 1 1 0%;
		padding: 24px;
	}

	.css-6vt7sa {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		width: 40px;
		height: 40px;
		border-radius: 100%;
		border-width: 1px;
		border-style: solid;
		border-color: rgb(30, 35, 41);
		font-weight: 500;
		-webkit-box-pack: center;
		justify-content: center;
		-webkit-box-align: center;
		align-items: center;
		font-size: 20px;
		color: rgb(30, 35, 41);
	}

	.css-1sgz1lk {
		box-sizing: border-box;
		margin: 0px 0px 0px 16px;
		min-width: 0px;
	}

	.css-ize0sl {
		box-sizing: border-box;
		margin: 0px 0px 4px;
		min-width: 0px;
		display: flex;
		-webkit-box-align: center;
		align-items: center;
	}

	.css-1uoge8i {
		box-sizing: border-box;
		margin: 0px 16px 0px 0px;
		min-width: 0px;
	}

	.css-180eiyx {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		font-size: 14px;
		color: rgb(30, 35, 41);
	}

	.css-1ap5wc6 {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		color: rgb(112, 122, 138);
	}

	.css-1124n14 {
		box-sizing: border-box;
		margin: 0px 16px 0px 0px;
		min-width: 0px;
		color: rgb(30, 35, 41);
	}

	.css-lzd0h4 {
		box-sizing: border-box;
		margin: 0px 0px 0px 8px;
		min-width: 0px;
	}

	.css-bhso1m {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: inline-block;
		border-radius: 4px;
		padding-left: 8px;
		padding-right: 8px;
		font-size: 14px;
		background-color: #00b897;
		color: #fff;
	}

	.css-1ry7rnu {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		color: rgb(112, 122, 138);
		font-size: 12px;
		line-height: 1.25;
	}

	.css-9cwl6c {
		box-sizing: border-box;
		margin: 0px 8px 0px 0px;
		min-width: 0px;
	}

	.css-vurnku {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
	}

	.css-kvkii2 {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		width: 66.6667%;
		padding: 8px;
	}

	.css-1p01izn {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		box-shadow: rgb(20 21 26 / 4%) 0px 1px 2px, rgb(71 77 87 / 4%) 0px 3px 6px, rgb(20 21 26 / 10%) 0px 0px 1px;
		border-radius: 4px;
		background-color: rgb(255, 255, 255);
		padding: 0px 16px;
		width: 100%;
	}

	.css-1hythwr {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		flex: 1 1 0%;
		flex-direction: column;
	}

	.css-1hz1mz6 {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		flex: 1 1 0%;
		padding-top: 16px;
		padding-bottom: 16px;
	}

	.css-181kvgz {
		box-sizing: border-box;
		margin: 0px 16px 0px 0px;
		min-width: 0px;
		display: inline-block;
		text-decoration: none;
		font-weight: 600;
		color: rgb(30, 35, 41);
		font-size: 16px;
		padding: 0px;
	}

		.css-181kvgz:hover {
			color: rgb(252, 213, 53);
		}

	a, a:active, a:visited {
		text-decoration: none;
	}

	.css-10nf7hq {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		-webkit-box-align: center;
		align-items: center;
	}

	.css-4cffwv {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
	}

	.css-noqr05 {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		-webkit-box-pack: justify;
		justify-content: space-between;
		-webkit-box-align: center;
		align-items: center;
		width: auto;
	}

	.css-d732j0 {
		margin: 0px 4px;
		min-width: 0px;
		appearance: none;
		user-select: none;
		cursor: pointer;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		display: inline-flex;
		-webkit-box-align: center;
		align-items: center;
		-webkit-box-pack: center;
		justify-content: center;
		box-sizing: border-box;
		font-family: inherit;
		text-align: center;
		text-decoration: none;
		outline: none;
		font-weight: 500;
		line-height: 20px;
		word-break: keep-all;
		color: rgb(33, 40, 51);
		border-radius: 4px;
		padding: 4px 16px;
		border: none;
		background: #3db485;
		color: #fff;
		flex: 1 1 0%;
		min-height: 24px;
		font-size: 12px;
	}

	.css-1dfql01 {
		margin: 0px 4px;
		min-width: 0px;
		appearance: none;
		user-select: none;
		cursor: pointer;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		display: inline-flex;
		-webkit-box-align: center;
		align-items: center;
		-webkit-box-pack: center;
		justify-content: center;
		box-sizing: border-box;
		font-family: inherit;
		text-align: center;
		text-decoration: none;
		outline: none;
		font-weight: 500;
		line-height: 20px;
		word-break: keep-all;
		color: rgb(33, 40, 51);
		border-radius: 4px;
		padding: 4px 16px;
		border: none;
		background-color: transparent;
		box-shadow: rgb(234 236 239) 0px 0px 0px 1px inset;
		flex: 1 1 0%;
		min-height: 24px;
		font-size: 12px;
	}

	.css-1s6nhe2 {
		box-sizing: border-box;
		margin: 0px 0px 0px 16px;
		min-width: 0px;
		text-decoration: none;
		color: rgb(201, 148, 0);
		display: flex;
	}

	.css-155meta {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		color: rgb(0, 0, 0);
		font-size: 18px;
		fill: rgb(0, 0, 0);
		width: 1em;
		height: 1em;
	}

	.css-1s6nhe2:hover {
		text-decoration: underline;
		color: #00b897;
	}

	.css-vurnku {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
	}

	.css-gnqbje {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: block;
	}

	.css-ysetcg {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		padding-top: 48px;
		padding-bottom: 48px;
	}

	.css-ct0qa6 {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		flex: 1 1 0%;
		flex-flow: column wrap;
		flex-direction: row;
	}

	.css-1bliacb {
		box-sizing: border-box;
		margin: 0px;
		min-width: 250px;
		flex: 1 1 0%;
		margin-bottom: 24px;
	}

	.css-vurnku {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
	}

	.css-1f978ju {
		box-sizing: border-box;
		margin: 0px 0px 8px;
		min-width: 0px;
	}

	.css-ize0sl {
		box-sizing: border-box;
		margin: 0px 0px 4px;
		min-width: 0px;
		display: flex;
		-webkit-box-align: center;
		align-items: center;
	}

	.css-1kbdyxh {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		font-size: 14px;
		color: rgb(112, 122, 138);
		display: inline-block;
	}

	.css-n5mzgu {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		align-items: flex-end;
		flex-wrap: wrap;
		color: rgb(30, 35, 41);
		font-size: 14px;
		line-height: 24px;
	}

	.css-off8uh {
		box-sizing: border-box;
		margin: 0px 4px 0px 0px;
		min-width: 0px;
		display: flex;
		align-items: flex-end;
	}

	.css-1t9tl2o {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		font-size: 32px;
		line-height: 36px;
	}

	.css-vurnku {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
	}

	.css-omwf4y {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		width: 100%;
		padding: 8px;
		flex-direction: column;
		flex: 1 1 0%;
	}

	.css-vt77s9 {
		box-sizing: border-box;
		/* margin: 16px 0px 0px; */
		min-width: 0px;
		box-shadow: rgb(20 21 26 / 4%) 0px 1px 2px, rgb(71 77 87 / 4%) 0px 3px 6px, rgb(20 21 26 / 10%) 0px 0px 1px;
		border-radius: 4px;
		background-color: rgb(255, 255, 255);
		padding: 0px 16px;
		width: 100%;
		flex: 1 1 0%;
	}

	.css-1hythwr {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		flex: 1 1 0%;
		flex-direction: column;
	}

	.css-hwv82q {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		border-width: 0px 0px 1px;
		border-style: solid;
		border-color: rgb(234, 236, 239);
		flex: 1 1 0%;
		padding-top: 16px;
		padding-bottom: 16px;
	}

	.css-5x6ly7 {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		flex: 1 1 0%;
		-webkit-box-align: center;
		align-items: center;
	}

	.css-65w75 {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		font-size: 12px;
	}

	.css-1wuyyej {
		box-sizing: border-box;
		margin: 8px 0px 0px;
		min-width: 0px;
		text-decoration: none;
		display: block;
		border-width: 0px 0px 1px;
		border-style: solid;
		border-color: rgb(234, 236, 239);
		color: rgb(30, 35, 41);
		line-height: 1.5;
		padding-top: 16px;
		padding-bottom: 16px;
	}

	.css-1joqi3u {
		box-sizing: border-box;
		margin: 16px 0px 0px;
		min-width: 0px;
		text-align: right;
		color: rgb(112, 122, 138);
	}

	.css-1wuyyej:hover {
		color: #00b897;
	}

	a, .css-1wuyyej:active, .css-1wuyyej:visited {
		text-decoration: none;
	}

	.css-ei3nni {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		width: 100%;
		padding: 8px;
	}

	.css-1p01izn {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		box-shadow: rgb(20 21 26 / 4%) 0px 1px 2px, rgb(71 77 87 / 4%) 0px 3px 6px, rgb(20 21 26 / 10%) 0px 0px 1px;
		border-radius: 4px;
		background-color: rgb(255, 255, 255);
		padding: 0px 16px;
		width: 100%;
	}

	.css-1hythwr {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		flex: 1 1 0%;
		flex-direction: column;
	}

	.css-hwv82q {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		border-width: 0px 0px 1px;
		border-style: solid;
		border-color: rgb(234, 236, 239);
		flex: 1 1 0%;
		padding-top: 16px;
		padding-bottom: 16px;
	}

	.css-1dcd6pv {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		flex: 1 1 0%;
		padding-top: 16px;
		padding-bottom: 16px;
		flex-direction: column;
	}

	.css-1h690ep {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		flex: 1 1 0%;
	}

	.css-phax11 {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		padding: 8px;
		flex-direction: column;
		width: 20%;
	}

	.css-9b6x94 {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		font-size: 12px;
		color: rgb(112, 122, 138);
		line-height: 1.5;
	}

	.css-1ngrbny {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		padding: 8px;
		flex-direction: row;
		width: 20%;
		-webkit-box-pack: end;
		justify-content: flex-end;
		-webkit-box-align: center;
		align-items: center;
	}

	.css-1pysja1 {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		flex: 1 1 0%;
	}

	.css-kc3i4p {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
		border-width: 0px 0px 1px;
		border-style: solid;
		border-color: rgb(234, 236, 239);
		flex: 1 1 0%;
		padding-top: 4px;
		padding-bottom: 4px;
	}

	.css-1g02g2m {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		font-size: 14px;
		color: rgb(30, 35, 41);
	}

	.css-cvky42 {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		font-size: 12px;
		color: rgb(30, 35, 41);
	}

	.css-4cffwv {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		display: flex;
	}

	.css-1pryf6p {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		font-size: 12px;
		color: rgb(14, 203, 129);
	}

	.css-1g02g2m {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		font-size: 14px;
		color: rgb(30, 35, 41);
	}

	.css-9biujf {
		box-sizing: border-box;
		margin: 0px;
		min-width: 0px;
		cursor: pointer;
		text-decoration: underline;
		font-size: 12px;
		color: #00b897;
		line-height: 1.5;
	}

	.textbox_3 {
		width: 100%;
		height: 60px;
		padding: 5px;
		border: 1px solid #f5f5f5;
		border-radius: 5px;
	}

	textarea {
		background: #fff;
		color: #000000;
	}

	textarea {
		overflow: auto;
		resize: vertical;
	}

	.contentbox_1 {
		width: 100%;
		margin-bottom: 15px;
	}

	.contentbox_2 {
		min-width: 60%;
		max-width: 80%;
		background: #f5f5f5;
		float: right;
		padding: 5px 10px;
		border-radius: 5px;
		margin-bottom: 15px;
	}

	.contentbox_3 {
		min-width: 60%;
		max-width: 80%;
		background: #f5f5f5;
		float: right;
		padding: 5px 10px;
		border-radius: 5px;
		margin-bottom: 15px;
		float: left;
	}
</style>
<div id="main-content">
	<div id="top-alert" class="fixed alert alert-error" style="display: none;">
		<button class="close fixed" style="margin-top: 4px;">&times;</button>
		<div class="alert-content">警告内容</div>
	</div>
	<div id="main" class="main">
		<div class="main-title-h">
			<span class="h1-title">在线客服</span>
		</div>

		<div class="css-vurnku">
			<div class="css-gnqbje">
				<div class="css-ysetcg" style="width: 100%;padding: 10px;margin: 0px;min-height: 400px;">
					<div style="width:100%;min-height:30px;margin-bottom: 20px;">
						<div style="width:100%;height:50px;line-height:50px;padding: 0px 10px;background-color: #f5f5f5;color: #00b897;font-size:12px;">
							<?php echo L('客户提交的问题');?>
						</div>
					</div>
					<div style=" width: 100%;height: 240px;background: #fff;overflow: auto; padding: 0px 20px 20px 20px;margin-bottom:20px;" id="msgbox">

					</div>
					<div style="width:100%;background:#fff;min-height:60px;border-top:1px solid #f5f5f5;padding: 10px 0px;">
						<div style="width:75%;heihgt:60px;float:left;">
							<textarea class="textbox_3" id="content" placeholder="<?php echo L('请输入信息内容');?>"></textarea>
						</div>
						<input type="hidden" id="flag" value="1" />
						<div style="width:20%;heihgt:60px;float:right;">
							<div style="width:100%;height:60px;line-height:60px;text-align:center;background: #00b897;
color: #fff;border-radius:5px;" id="sendbtn">
								<span class=" f12"><?php echo L('发送');?></span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>


</div>
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/Public/Home/static/js/layer/layer.js"></script>
<script type="text/javascript" src="/Public/Home/static/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript">
	$(function () {
		getchatlist();
	});

	setInterval(function () {
		getchatlist();
	}, 1000);
	function GetQueryString(name) {
		var reg = new RegExp("(^|)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;

	}
</script>
<script type="text/javascript">
	//提交表单
	$('#submit').click(function () {
		$('#form').submit();
	});
</script>
<script type="text/javascript">
	function scrollToBottom() {
		var msgBox = document.getElementById("msgbox");
		msgBox.scrollTop = msgBox.scrollHeight;
	};
	function clearContent() {
		var input = document.getElementById("content");
		input.value = "";
	};
</script>

<script type="text/javascript">
	function getchatlist() {
		$.post("<?php echo U('User/getlineinfo');?>", {
			id: GetQueryString('id')
		}, function (data) {
			if (data.code = 1) {
				$("#msgbox").empty();
				var html = '';
				if (data.data == '' || data.data == null) {
					$("#msgbox").empty();
				} else {
					$.each(data.data, function (key, val) {
						if (val.type == 2) {
							html += '<div class="contentbox_1" style="height:40px;margin-bottom:0px;">' +
								'<div class="contentbox_3">' +
								'<span class="fcc f14">' + val.content + '</span>' +
                                '<span class="fcc f14">' + '('+val.addtime + ')'+'</span>' +
								'</div>' +
								'</div>';
						} else if (val.type == 1) {
							html += '<div class="contentbox_1">' +
								'<div class="contentbox_2">' +
								'<span class="fcc f14">' + val.content + '</span>' +
                                '<span class="fcc f14">' + '(' + val.addtime + ')' + '</span>' +
								'</div>' +
								'</div>';
						}
					});
					$("#msgbox").append(html);
					scrollToBottom();
				}

			} else {
				console.log("出错了"); return false;
			}
		});
	}
</script>
<script type="text/javascript">
	$("#sendbtn").click(function () {
		var flag = $("#flag").val();
		if (flag == 2) {
			return false;
		}
		var txt = $("#content").val();
		if (txt == '' || txt == null) {
			layer.msg("<?php echo L('请输入信息内容');?>"); return false;
		}
		$("#flag").val(2);
		$.post("<?php echo U('User/uptxt');?>",
			{
				'txt': txt,
                'uid': GetQueryString('id')
			},
			function (data) {
				if (data.code == 1) {
					$("#content").val();
                    clearContent();
                    //重新启用按钮（假设5秒重新启用)
                    setTimeout(function () {
						$("#flag").val(1);
                    }, 5000);
				} else {
					layer.msg(data.info); return false;
				}
			});

	});
</script>
<script type="text/javascript">
	$("#nav").slide({
		type: "menu",// 效果类型，针对菜单/导航而引入的参数（默认slide）
		titCell: ".nLi", //鼠标触发对象
		targetCell: ".sub", //titCell里面包含的要显示/消失的对象
		effect: "slideDown", //targetCell下拉效果
		delayTime: 300, //效果时间
		triggerTime: 0, //鼠标延迟触发时间（默认150）
		returnDefault: true //鼠标移走后返回默认状态，例如默认频道是“预告片”，鼠标移走后会返回“预告片”（默认false）
	});
</script>

<script type="text/javascript" src="/Public/Admin/js/common.js"></script>
<script type="text/javascript">
	+function(){
		//$("select").select2({dropdownCssClass: 'dropdown-inverse'});//下拉条样式
		layer.config({
			extend: 'extend/layer.ext.js'
		});

		var $window = $(window), $subnav = $("#subnav"), url;
		$window.resize(function(){
			//$("#main").css("min-height", $window.height() - 90);
		}).resize();

		/* 左边菜单高亮 */
		url = window.location.pathname + window.location.search;

		url = url.replace(/(\/(p)\/\d+)|(&p=\d+)|(\/(id)\/\d+)|(&id=\d+)|(\/(group)\/\d+)|(&group=\d+)/, "");
		$subnav.find("a[href='" + url + "']").parent().addClass("current");

		/* 左边菜单显示收起 */
		$("#subnav").on("click", "h3", function(){
			var $this = $(this);
			$this.find(".icon").toggleClass("icon-fold");
			$this.next().slideToggle("fast").siblings(".side-sub-menu:visible").
			prev("h3").find("i").addClass("icon-fold").end().end().hide();
		});

		$("#subnav h3 a").click(function(e){e.stopPropagation()});

		/* 头部管理员菜单 */
		$(".user-bar").mouseenter(function(){
			var userMenu = $(this).children(".user-menu ");
			userMenu.removeClass("hidden");
			clearTimeout(userMenu.data("timeout"));
		}).mouseleave(function(){
			var userMenu = $(this).children(".user-menu");
			userMenu.data("timeout") && clearTimeout(userMenu.data("timeout"));
			userMenu.data("timeout", setTimeout(function(){userMenu.addClass("hidden")}, 100));
		});

		/* 表单获取焦点变色 */
		$("form").on("focus", "input", function(){
			$(this).addClass('focus');
		}).on("blur","input",function(){
			$(this).removeClass('focus');
		});
		$("form").on("focus", "textarea", function(){
			$(this).closest('label').addClass('focus');
		}).on("blur","textarea",function(){
			$(this).closest('label').removeClass('focus');
		});

		// 导航栏超出窗口高度后的模拟滚动条
		var sHeight = $(".sidebar").height();
		var subHeight  = $(".subnav").height();
		var diff = subHeight - sHeight; //250
		var sub = $(".subnav");

	}();

	//导航高亮
	function highlight_subnav(url){
		$('.side-sub-menu').find('a[href="'+url+'"]').closest('li').addClass('current');
	}

	function lockscreen(){
		layer.prompt({
			title: '输入一个锁屏密码',
			formType: 1,
			btn: ['锁屏','取消'] //按钮
		}, function(pass){
			if(!pass){
				layer.msg('需要输入一个密码!');
			}else{
				$.post("<?php echo U('Login/lockScreen');?>",{pass:pass},function(data){
					layer.msg(data.info);
					layer.close();
					if(data.status){
						window.location.href = "<?php echo U('Login/lockScreen');?>";
					}
				},'json');
			}
		});
	}
</script>
<div style="display:none;">

</div>
</body>
</html>

	<script type="text/javascript" charset="utf-8">
		//导航高亮
		highlight_subnav("<?php echo U('User/online');?>");
	</script>