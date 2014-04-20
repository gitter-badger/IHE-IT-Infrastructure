<html>
	<head>
		<script type='text/javascript' src='http://code.jquery.com/jquery-1.7.1.js'></script>
		<script type='text/javascript' src="http://cdn.kendostatic.com/2011.3.1129/js/kendo.all.min.js"></script>
		<!-- http://demos.telerik.com/kendo-ui/web/splitter/index.html -->
		<link rel="stylesheet" type="text/css" href="http://cdn.kendostatic.com/2011.3.1129/styles/kendo.common.min.css">
		<link rel="stylesheet" type="text/css" href="http://cdn.kendostatic.com/2011.3.1129/styles/kendo.default.min.css">
		<link href="./assets/css/bootstrap.css" rel="stylesheet">
		<link href="./assets/css/bootstrap-responsive.css" rel="stylesheet">
		<title>Being Java Guys | Hello World</title>
        <style scoped>
            #vertical {
                margin: 0 auto;
            }

            #middle-pane { background-color: rgba(60, 70, 80, 0.10); }
            #bottom-pane { background-color: rgba(60, 70, 80, 0.15); }
            #left-pane, #center-pane, #right-pane  { background-color: rgba(60, 70, 80, 0.05); }

            .pane-content {
                padding: 0 10px;
            }
        </style>
	</head>
	<body>
		<div class="container-fluid">
			<div class="row">
				<ul class="nav nav-pills pull-right">
					<li class="active"><a href="index.html">Home</a></li>
					<li><a
						href="https://github.com/Gaduo/Integrating-The-Healthcare-Enterprise">下載</a></li>
				</ul>
				<hr />
				<div id="example" class="k-content">
					<div id="vertical">
						<div id="top-pane">
							<div id="horizontal" style="height: 100%; width: 100%;">
								<div id="left-pane">
									<div class="pane-content">
										<h3>Inner splitter / left pane</h3>
										<p>Resizable and collapsible.</p>
									</div>
								</div>
								<div id="center-pane">
									<div class="pane-content">
										<h3>Inner splitter / center pane</h3>
										<p>Resizable only.</p>
									</div>
								</div>
								<div>
									<h3>Inner splitter / right pane</h3>
									<p>Resizable and collapsible.</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<script src="../assets/js/bootstrap-transition.js"></script>
		<script src="../assets/js/bootstrap-alert.js"></script>
		<script src="../assets/js/bootstrap-modal.js"></script>
		<script src="../assets/js/bootstrap-dropdown.js"></script>
		<script src="../assets/js/bootstrap-scrollspy.js"></script>
		<script src="../assets/js/bootstrap-tab.js"></script>
		<script src="../assets/js/bootstrap-tooltip.js"></script>
		<script src="../assets/js/bootstrap-popover.js"></script>
		<script src="../assets/js/bootstrap-button.js"></script>
		<script src="../assets/js/bootstrap-collapse.js"></script>
		<script src="../assets/js/bootstrap-carousel.js"></script>
		<script src="../assets/js/bootstrap-typeahead.js"></script>
	
		<script>
	            $(document).ready(function() {
	                $("#vertical").kendoSplitter({
	                    orientation: "vertical",
	                    panes: [
	                        { collapsible: false },
	                        { collapsible: false, size: "100px" },
	                        { collapsible: false, resizable: false, size: "100px" }
	                    ]
	                });
	
	                $("#horizontal").kendoSplitter({
	                    panes: [
	                        { collapsible: true, size: "220px" },
	                        { collapsible: false },
	                        { collapsible: true, size: "220px" }
	                    ]
	                });
	            });
		</script>
	</body>
</html>
