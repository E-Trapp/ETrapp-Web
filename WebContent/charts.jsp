<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Montserrat|Ubuntu" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
<link rel="stylesheet" href="css/style.css" >

<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.min.js"></script>


</head>
<body>
	<div class="container-fluid pageControl">
		<div class="row">
			<jsp:include page="control_menu.jsp"></jsp:include>
			
			<div class="col-2"></div>
			
			<div class="col-10 content">
				<div class="content-fluid">
					<div class="row header">
						<div class="col-12">
							<h1>Charts Dashboard</h1>
						</div>
					</div>
					
					<div class="row barmenu">
						<div class="col-12">
							<div class="input-group">
								<input type="text" class="form-control" placeholder="Busco un evento...">
									<span class="input-group-btn">
									<button class="btn btn-secondary" type="button">Go!</button>
								</span>
							</div>
						</div>
					</div>
					
					
					<div class="row charts">
						<div class="col-4">
							<canvas id="chart1"></canvas>
						</div>
						
						<div class="col-4">
							<canvas id="chart2"></canvas>
						
						</div>
						
						<div class="col-4">
							<canvas id="chart3"></canvas>
						</div>
					</div>
					
					<div class="row charts">
						<div class="col-4">
							<canvas id="chart4"></canvas>
						</div>
						
						<div class="col-4">
							<canvas id="chart5"></canvas>
						
						</div>
						
						<div class="col-4">
							<canvas id="chart6"></canvas>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>
	
	<script>

		var ctx = document.getElementById('chart1').getContext('2d');
		var chart = new Chart(ctx, {
		    // The type of chart we want to create
		    type: 'line',
		
		    // The data for our dataset
		    data: {
		        labels: ["January", "February", "March", "April", "May", "June", "July"],
		        datasets: [{
		            label: "My First dataset",
		            backgroundColor: 'rgb(255, 99, 132)',
		            borderColor: 'rgb(255, 99, 132)',
		            data: [0, 10, 5, 2, 20, 30, 45],
		        }]
		    },
		
		    // Configuration options go here
		    options: {}
		});

		var ctx = document.getElementById('chart2').getContext('2d');
		var myLineChart = new Chart(ctx, {
		    type: 'line',
		    data: {
		        labels: ['M', 'T', 'W', 'T', 'F', 'S', 'S'],
		        datasets: [{
		          label: 'apples',
		          data: [12, 19, 3, 17, 6, 3, 7],
		          backgroundColor: "rgba(153,255,51,0.4)"
		        }, {
		          label: 'oranges',
		          data: [2, 29, 5, 5, 2, 3, 10],
		          backgroundColor: "rgba(255,153,0,0.4)"
		        }]
		      }
		});


		var ctx = document.getElementById('chart3').getContext('2d');
		var myChart = new Chart(ctx, {
		  type: 'bar',
		  data: {
		    labels: ["M", "T", "W", "T", "F", "S", "S"],
		    datasets: [{
		      label: 'apples',
		      data: [12, 19, 3, 17, 28, 24, 7],
		      backgroundColor: "rgba(153,255,51,1)"
		    }, {
		      label: 'oranges',
		      data: [30, 29, 5, 5, 20, 3, 10],
		      backgroundColor: "rgba(255,153,0,1)"
		    }]
		  }
		});

		var ctx = document.getElementById("chart4");
		var myChart = new Chart(ctx, {
		  type: 'radar',
		  data: {
		    labels: ["M", "T", "W", "T", "F", "S", "S"],
		    datasets: [{
		      label: 'apples',
		      backgroundColor: "rgba(153,255,51,0.4)",
		      borderColor: "rgba(153,255,51,1)",
		      data: [12, 19, 3, 17, 28, 24, 7]
		    }, {
		      label: 'oranges',
		      backgroundColor: "rgba(255,153,0,0.4)",
		      borderColor: "rgba(255,153,0,1)",
		      data: [30, 29, 5, 5, 20, 3, 10]
		    }]
		  }
		});

		var ctx = document.getElementById("chart5").getContext('2d');
		var myChart = new Chart(ctx, {
		  type: 'polarArea',
		  data: {
		    labels: ["M", "T", "W", "T", "F", "S", "S"],
		    datasets: [{
		      backgroundColor: [
		        "#2ecc71",
		        "#3498db",
		        "#95a5a6",
		        "#9b59b6",
		        "#f1c40f",
		        "#e74c3c",
		        "#34495e"
		      ],
		      data: [12, 19, 3, 17, 28, 24, 7]
		    }]
		  }
		});

		var ctx = document.getElementById("chart6").getContext('2d');
		var myChart = new Chart(ctx, {
		  type: 'pie',
		  data: {
		    labels: ["M", "T", "W", "T", "F", "S", "S"],
		    datasets: [{
		      backgroundColor: [
		        "#2ecc71",
		        "#3498db",
		        "#95a5a6",
		        "#9b59b6",
		        "#f1c40f",
		        "#e74c3c",
		        "#34495e"
		      ],
		      data: [12, 19, 3, 17, 28, 24, 7]
		    }]
		  }
		});
		
	</script>
</body>
</html>