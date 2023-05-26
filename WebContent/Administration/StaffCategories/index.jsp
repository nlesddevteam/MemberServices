<%@ page language="java"
         session="true"
         import="java.util.*,com.awsd.security.*"%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />

<% 
int newCatNum = 0;
int oldCatNum = 0;
int regCatNum = 0;
%>

<html>
  <head>
     <title>StaffRoom Categories</title>
   	<script src="includes/js/categories.js"></script>
   <style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
		td {vertical-align:middle;}
	</style>
	
	<script>		
		$('document').ready(function(){
			mTable = $(".rolesTable").dataTable({
				"order" : [[1,"asc"]],
				  "lengthMenu": [[25,50,100, 250, -1], [25,50,100, 250, "All"]],	
				responsive: true, 
				dom: 'Blfrtip',				       	
				
				 buttons: [
			        	{
			                extend: 'print',
			                orientation: 'landscape',
			                pageSize: 'LETTER',	
			                title:'',
			                exportOptions: {
			                   columns: [0,1,2]
			                },
			                customize: function ( win ) {
			                    $(win.document.body)
			                        .css( 'font-size', '10pt' )
			                        .prepend('<img src="https://www.nlesd.ca/includes/img/nlesd-colorlogo.png" style="text-align:center;max-width:200px;" />');
			 
			                    $(win.document.body).find( 'table' )
			                        .addClass( 'compact' )
			                        .css( 'font-size', 'inherit' );
			                }
			                
			                
			            },		           
			           {
			                extend: 'excelHtml5',		                	               
			                exportOptions: {
			                   columns: [0,1,2]
			                },
			                
			                },
			               	{
					                extend: 'pdfHtml5',
					                orientation: 'landscape',
					                pageSize: 'LETTER',		               
					                exportOptions: {
					                   columns: [0,1,2]
					                },
					                customize : function(doc){ 		
					                	doc.defaultStyle.fontSize = 9;
					                    doc.styles.tableHeader.alignment = 'center'; 
					                   doc.content[1].table.widths = ['30%','5%','10%','15%','15%','15%','10%']; 
					                    doc.content.splice( 1, 0, {
					    					margin: [ 0, 0, 0, 4 ],
					    					alignment: 'center',
					    					image: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAPoAAAA7CAYAAABSWJcsAAAACXBIWXMAAA5tAAAObQHU/UmdAAAgAElEQVR4nO19B5xU1fX/9743ZfuyCyxIkQ5SrGBD2F01SqzRJCamGE35mfKLvcOiq4JiicYkxvxMjCYx0cTknxhjFEtcQBQLCiqIUkRYOgsL23fevPv/nvvezLxZdmFWQM3HOXyGnXnv3vtuO/Wec57CXsLw6tKiSF7xhY6l5riRVW+tuBhtyEIWsvCpgr1G9GF3DCuLAvM13H5Kq5eh9EPKjv17yeW125CFLGThUwHWPmonqqEa+Xccm/yt60TmjL5zyHmjbutViCxkIQufOOwTRNfadSkaPOHEnYlao0rkBKWtB2xV/DgRvhzV+4ygZCELWfgIsG8QUFkN0Eq/f92aVe9evfJ2HWurdLW+ngg/luL846Pzhl05/GfDo8hCFrLwicBeI7qO6zZot5EsXCU497Kp6+qWXb1qluvgVK3VMqVwc6TNvWFsNSLIQhay8LHDXiP6qoWrGrVSLlF+BA6AHby37LqVC9vtlq/w62vU4S93c4d8FVnIQhY+dth70f0xxC2t3qHoPmrYzmElHW+vvGL9Wq2diwG3CZZ1aZ87+uTv0sak6eNRXnUqspCFLOwX2DfGOOW+ShW9KIz4mM7uv/vah4u1xlP8OrzYzRubdrPyil6w9f3U5z8PVGeNdlnIwn6AfYJYjuu+ZMFyLNhfCl4fe2/vAnPERq4PqCcVELUsnZdW2c39KpH8UGisJKK7yEIWsrDPYd9w9NzwKv55id++NPLOwQclb2zZ0m6pwrOHzhpa7MadV3ilMa3iUdf1hNI/4LfViOm/IgtZyMJ+gX2C6CsuXtHmav0LfnrY2rpz+C19e8v1JdVoV0q/F7XVVbFcu4n3Fzs6vjZZMc86D0odRG5+L16euQ5ZyEIW9gvsM514WfGq2Qrqfn6mREL5fx59x8Bxcn1p0+qF/FOW044pvPfCiuK1a0yFo6uLqNx/h0j+LlToD8hCFrKw32DfGb++j1isuf56iu8PUOc+Dgj9a/Ttg81xmobzoFLqx1o5z0s5Uz7XOcocyQH3oaZ6K7KQhSzsN7D3ZWPbalra8o8rfi4cUR+ShoxUyvpGr3BJX7fdedoKhU5mkS1bn61/0xQeVH4pCUJPuO5VWPNiMz6TcI6Nyq9EkV9pYUtNHJ8EjL8wjNIzQtgi2tZXFMZWhlHW28aWpfuvP2OrIyir5DMqNVCj8UlD2hws3b/9GXsOx/4V+2N5VgB2jV6rvHYw3NAYaLf7kW22FYfjvIIXZ23vV90vL68gNNhCKB9o/cByc09XClPeHbTym7jtmgIUhOeSo/8Zc2beIlWH/HRIn0i7NbwgmqNaYy0+2+8aQtqNqaamJUuqtzRib+CQK/NR2qywLU/jrYKWpOX/2MtyEW2zUWvHsOLn+z70tmLqKVDq+1RfDuQqtCHuzsC8W57ExwkTq8sQjv2G/TjArAWsWv69gp86uOoizJ2xfJ8/8/jph8I1x6kWx3wtx/z8roWqLRyLHgi1RxFvjyNSsI1Sn9Nlm+eQYL43KMesY4PThoX3xzLuz+TresO2/8JvBWYOambeif0FR0/tgxzrLxx7HsXcP2HOjLvxMUFolys6dAaX4C4u+h4RXbFIYW4UO5ta/br8WKFr+P9P1levFy69NFF29MxBs1XE/s7wpaX5Kwoih0C5RdxMjyTuR2M4h839RGtl9S3sjw071+/22Rr2pva8nAp0tOR3ByZfNwaWzT7k2Chlk5Nj1ZiHv5nTgKj9KyB/NAboR7Gi+pZ9evQ3afrRHMHD/FbKRd8BrTfB1ivxcUNEXJLV8ZBNrjEXSvfl9wkkPhtgtTXsl2fG4+O5SSaI9wXnfucu9wUZcrkOWk1huQJYOURa5wWu1WWYd+uWTtvcNOo0zuRNXMcQCnElrzydcX+U3YPzP4nELsQ5eAz7E6JWKf+nWktJWuNhfIzQiY6uw/AIgL2nz8FD+tgh20pdU/xY+CbGX1PcsdVxiz7cTDrwkpVbLMdvB/PzHDnGh4n7CkqeGSE3Dw3scWCorLBPyO9Hpx8FbUebWzdhb0CpwyTwht/kMw62+gGGXxRFOB7l4h9prmsckIbkIubJZ2/A1ifwuUJatlFyOofqyyTU3LrM3KusDnnt76XzULKdjsB2Ky/IMWqD09KTF3L9G+/xc6j5ptCOLfnbkuVP4ZxI+T2BPE+euwuwrsyrtKVUD8PNlWqGiu/KeXOsWzkv32eZQu7FBfxbxLX4OhHywi6fKyqg9F3rMSQkLV32zfShA1gYxb54TE2rRaavolp0CRxDcF67ajdYPjF/Sh+UlKEtLN6lf3vaVzK3nc7vnqGzSqMyqVhalIfRB/bG26s64JomchSETuC3vycuNQ8Y2d96BdHDjmp7XLmhAg64HNq6l7eSCBQNRcqKc3pgS+MWrNi6HFNGnYJHF/2Ra9e5GsOrm5eu37J3IrWy+vABsul28Fc+RcqJOKBgAtz4B7zW0y9UZ/5MnnoEVZMfESnH8VocFVVPY3vrXSiJTOSqfRsiWSjndjTkrEdhbDo3zSAuajN1kDvQ0FqLkpwbWGYADDXXE+DtLSKc+iEsfTcmVhUhjPOhnQoUlkVR6ayFW/UIJaR/oqElH4XhmRy0EIfnSSAfoGpRikj+T2HWUFMqUcMh3BhYQOLRBMS+joKyXJRX/Ruxprvw8t0t/D6afbyK3TgElbqW412dmgv1Pn+X+oLcB+jNtZk89RBY7VehpXg0KorXQVfdw2edw7J5lMju5hjPNs/V+hn+7cvNewa5bwzl036FuTP/bNqtmEqJQV3FOmXo77zBKzGz2bVIMlb6+o2vZrvOIdIZ3n8C7eErEYnN4U/OeQdHq3QoMP9rtHC+0iUvUcEi+d/jt3NIOiLoV8V9SYlFaUoMej5HGRYuw39ikzgSlSMv5xqUorLqDbTpm2HFdsIOXQ7LGsJxPs/+nQiUDecYr2Y7R7Lel/x2H+N3IY6fY3/noWbZr9nW4Wzrcs7fSFQUyWnTds6x7Ld2zl+9Qf7JIyo55yRkiuoy/1VUvc55/TXm3vw2+1DJK98y+0hjIXTsPD5zNZH9wt2qMp3Aroiu1NhMKp48YRg+2LA9iYhJoigSgca3SZ3+ldCVKGaf4sb1ksb18UXhwpLTeWEVKfRrwfbandjhk4dU4JU1C7BuRy2KokUgZ8ea7R920QO9DP2wd8YMza1pmY6zn9z0liCLdQF/i+6U4xd6E5OnVxAZRc0oI3FYb7gxhUwibysnYD1b+QrbiiMe/h3yY2VcqEtYRjhlnGj9J5Tksry+iNdkvmWBfA6gWUadzmc+h7AWXXkkLzaZthTbV/o0NnExIqGnuEG+zf5xs2vPDyGUJ4h9LsuFqQL9lX/P5mcyr53KPuaaZ3hLciTLLsPEaa+zn6KLjmP7RAZ1iN8fKdXKKxHDaU239PtQbcO5uR8zfZLy4r2oUMGxibTGDW2JmPs1XhvBJj7H772QkBBlD02+7m3qvmWAEYd7mrkwHpDK3zDSZuOGtPVYWN2MimmvsP543j8VUWcICx5kjmC1eshwvIXb2ffH0g2FypdELNXCVutTN8hNI7FreX+qzBjbESJzuMRcmvXR2Mq/q5PjVrhRMin483IkIjqfxOZaSmDf4e8Dee8s/i1mPRIK9T2WOy+tXWX2o0iDDspHzeEv0ceHePOnxDZhm/2m/T6Wj5I27jDSi4YgUz7vH8v+nY7jp59Bgk0JQH3bR7JWfue66p92F8nN1KT9qqzO4QPz9lQpEg7h9GNHYcW6VLaoiWMHIi+akDxUJXJLD0nccy01xbGs0bV317bsWDtoAa+Q4lW3JitXm4nt8+H21Thr3JcM0djavBXHDzuxyz4obdWzXrcHnAYWF8GDTZA0WAKaXMmyj/a/S1RePZH8Ji4GRXg8S45+Oq/eDG24wDe4GG/yexu/R2C5ZYbCQ8s8CgXnwuojDPdQkGtisyDXExHRjOIFXvs8733RR6jNbO9sH3Fq/YW9FBFrKDdAQpz06tp6pOmBIJBw8CSSGjVqFj+3sZ+uIQS2OsWTFiQDEPuqcTXHcQrvJewC9ULy+Le31y1Fwmd91+uTJvLoS/iMMw0X9oiHQ061yZ8TQRCKy/oyfvO9G3Uv9rc/v1wBD8k3Iq6/yoKCGE1eEc2/PdLtK2IYFbXB29h9+BGOtpzc7ysGqQrK5qBi5BWdrORw/y9XLFabvDqpdbCRmDxk/A8J4kks8iuzPl6/ZX76+P3hXtI/4TjldGih14B1AsKWEPfefottHAf3ghYD8hfgSVPPsy65uL6ffyN+u9v5+8cGyV0t3JiSIGRjb/Dvt7MvlF5wg6eioIYzeixX4BJ4BJHEwb2AZfL99eBKq1fYzsWs+VN8BOigBzb18HX03ULPolyUFOZiZ3Nbsh/nTzk8xV6l87b9rVQNtY5Ffryl16h+2x48fB1aI/Pl6nIMj67v1y/voEh/cglt1ZKTH9z3EETsCJZveQ8HH3AomUrnqiEZw94f/2gc4v+t840jQjVl4U/iogkaSUIN4UoT/BqTKL7P5qxdZRBL+xsdWGH+V4ocGKd7qoB+xb93DD+nmc2rKBW0Nb3Bv55erPA2yy3ngh7sd+hfmBN5HnNs2Wib/L5RXCexMN8FASxP3NaWcCQhkFQZLOEiicjBp1Dz3s2UoH/JQo5fj/1RFb7IvJii/C+NtVurl/w6lEbczSl1RRBZlftjegUbdv4ac255ivU/8PtNDmaLQSnq9/EezJn5MxLwJ33uReJjyQY+0r//AubN/Bv7NJu/1vhtLEPNknSJrCRKzqsu4mc7f73uI/wg9uVrbES46tFsK11XFNE8IZlqEuXtOU3JezYJtid9xUnY7sK8m8ll4w/BhFUboDoiHN50aAP7N4PjJPHVf/H7SPyIjzbE0nvAvRzHDUTenazXw5s3fQfVlLlcQ1EJfJuD5n5Qh/vzV4O60IOY+57Mja8qcV1EakGSyNyF+TPeQyz8T/7ybAyaSG7hML+fdZzVCzFnxs/x0syuRNzdQgdEVyF2oO+eKolu3tjSnvxdnB9Fn9IClBTkBJpSX0Tl1aKTUuV1n+WfMTm5oT/u7PfoMQ0fPhzd2W/kQX0HRqeVoKSXY4uYq0ra4yR02sUBRf0MRxekj9pd2kXex96AGFDE0GP6qlch3i4c3UNOZYyFgshCjR3DrQU0EVPrl/mZy4/YIOQ4TCzBG/37wq2HmDJibPQa40YV0VJt5b74K+I5FN1UP7+8CysuRswEkjrG8De5rSfbKPT7QjXHbPiQ2aDKtT1DnR7uIy45tN7Ie/39NuqMaBsKHZgcq1ZLA+JpO6LF2nBPLqW55uo2EjCZC29Du8JFE3tDe9S8srqHITpev9fwM8L7Ln1Tr3vfLeXXa+L/LsukG2XdkNTv5c8LN/3YFKJXVvfiGCRUWeaDcxs6w0g8ykiY1/njex2t7r/S2oxaMo4C/9dyyk2ewaqyMmG4FYKUeo4KTTDSmNf3Jcn+CHcP52nPsKYq/HE2cBuUJMepKBUIWCK9mXHympVQRUaZ50m5uIj2/t6CL3VWHljCGh4h1UrmL6UqKeUht+2IxOUhkXYpZWKo34/NaNq8GnsB6YjeHonusjidwLD+pYiGU+p9OGSjIDeKgWWBqlr3h46cLV93qgaZIIp1upxi5HMqElkMOzyX89962/olta6OcrZ0byceQ9gOIy+cx2I2xKDfM79Xp33gln8bewMDcmQBE+JCPebfzkVVvwGMUeYg3+igufXJWZKcrJ1IJ9ReDC9vIO5cYY58lH7Wv1/mcXr9KBdzI+sJNTzM5whPo2blB8gP9U0SDkMkYo0Gmb0GzqaOejlVh1/wxwhT33V/y/a3+f1if9UsVDgPmTN4U0W/xm62sqwvJei3vLb0YIrPliEm2l3Nv95RliZXhPMblOT83tNXpY5awVKj/PssH1+ULC9qWP/i+1jnQZYf44+TRFbJuXPIGMBcx/Xr9vPH5fAaOY9OcO+TUT7tPo76YX5PEbngaUZDS8yfL4FjyPROMRKPJyH4tmr9N7x66/a0dYxHiKJ6oN/X8Sh0njHHcTjur1yDJtOm6Ny2NQOV037OQtN9aSxm+plSeUhInF9xrDLOE30J7M+GAHhEtgkJpI2rLX79EGvfhooqSjMmV6J3mmBLWb3R7+FJ6MV2kfNQiji677J9kQLb/GffjPKq//WOtQ1xquX3x5GQVJReisJ+e2WPSkd02x2eouRdQ9i2Y8P6lSZ/N7fGkBMJ4chR/VOFvMU5j9S1YGBtbUvBzuYfa6WmKWUt0nK+rt3rNq1t/0k16XyO7YoIHImGPEkw7sYxoNgIA0aM7whcg5iWRBZ7A25EkK+XWdCEgajN/jd/v8eP7YuNm+CG13EjXcXFWMtr5Sz7Jw7uYS7/ZaTmno7v6jVmQ5m2dC0JwHPUFRcZEde0RcnAdX9nOK3WgsDa2+juItT8ZCu/Xc1WPjS6rVI/MZZtIT7QM7G+4Y9wKBJDi4QgG+04o69qXWSMZmLhsKnDq0Sbyjumc81mtoz6YXRu96fmvN4jOt9ge+N9A5Bs6Pdh6Vyf0zUSf1uNqGukA2PU+7YnlfjltXB57SOfauTG3mG+iqhp7nMurJ3LWeSXhhCInq6UHI+JlLLVIyYdRPCFt+0whia5b4479QP8ewk8P4lNXj/V1USIs9Lqxe0iY6Q066V78+9Efo5jv/ojFqph32VNZS0pBqvvefMqdg2xgJP0aPbNMzbO599z+JdqgiA266H1Hs5N1N8LjRTPvXHaYXHyeQrJdvE9M8/e+rSwn2tZ9pfmGcYoqy/gs+XkYIc3DquZ6tDTvCZGX7F7HMO5E+J+kLFJaJdzFdtoThqkvEtp8CMY4IKQbnW31QjfyLRbaIs5uldxnlvWI99at3UnmlrbsXJdHc6aPAb3P/k62tr9Pml9KCmz6Hr/VvWr6znFszgzP4G3S1Idt6yxskXDROrS3FJsaFiPIwZMMAypoRO/DdKQnXFt7Z0hrmlLHQrLLvKMV46nBrxcvY1U/7vsyyB/ABswx0zw46i8djG0PZlPp26m2tiJN1Fnv+P1v/kZ6PxvGsSyqPfNv329OYvt7XzT6LEWKfeGhhdN2Xb9JkJm4ckr3AXm2twZ/8SkaxbBConVvJSbi4PWC1Bzq/TLxQrWH3/NV5EXkqMYEedCRhWw9TP4z8x13PxESNeziaiI1ydY88z5M5+IWPM7ePnubXzGFG7SE4yoqDGbG2qkOdfW1vvkwJIBaAOvt3I+VmHerIWYNJ3lXYqxqgExPiti9kdPjmelkSJc6s4yNkQ8G4ULcn5jkGvAnLJmVOJe6HYSdeswczrRjhqOnRIOpQHXeXmXNZkz8xFUTn3bGCOV6sX2NsBy51MPJle2DobREjg3IponNr4Tr0fY+paZ+zSIr8fLoXqMj11AKeos1hMisFhMQRz3wWYOnAilodhlhuO7VMeUdbhRicDntoZn45WZO3H89Be4Tl/j/VYipyd51VQ3ovLS84E8IToUOfVCs1dcdQjnkATfXom5eBflRFYtVnRrO+IxEh1rhJlv2W/ibTmgmtJF/FGO7xgj5QnDaOe6v3LLJmMYd2NVxohr7b0zVTpKl1ddwunao1Wv8rDBi5667fxxU676XWjuW57q8OWKsfjt1V/E2dP/iOffWBUoTXEL4XN3R5FG3z5UJvuuKSNPQeWwE1E1+1rc8LmbcPSBx+LMhz6Pdqc9rTyJ3CZXx45475q165GFLGRhj5BOBZUuzaTSBxt3bgiF7K1yxJZQnwS5V67fhqnfqEBhXtBRSJ2IePuY3beowiErhImDJ+GJpX83Z+gTBh6FxRsWUWrdlT7wmbG2Fjv7JpgsZCFD6Gh1PyyTSuvr6vPa2pzlX5g0GhThzbXtDS249Y9zcOToAbj0yxMRspNN96C4+Y3dtacsPXpMn3FYtvldvLr2FXLyY4yzzDsb36LU1KmL+Yer16/+ZKK9spCF/0IIILrxZR6QSaVYzO3xwcZtTw89oATnnnBw8vr/m/cu7n5sPi4/ZyIu+uIxAcu8Ooc6R9fHdhqjP9i2Cn9Z/AjEIPe1w89DI3XzFz+Y23l5pZfvtVdcFrLwGYKUMa5ybCHgZBhIofOn3v/se/defqZz+TnHhZ5+dTmW19bBiccx8+G55PgNuPzLx2HckD64/1+v47Vl6wa58diZrHh/x5YknJV/oo2+0e2MMV/AEf3GY/b7T2FNfRe+AXJ2vLdecfsLggEOA3rGjW2is2sfta1PGj51faq2MLwu5eS1omdsnycZFcNqW52noxa3ud0Kg/2UPDOA2M0FmZyhG9Aq8sSry15/8N9vbD+wTzFuu/DkpF7eHnPwf/98DZWXPYDfz16EeNwV9145X/yWCVroAIW5tlixzRna2L4H44fHXoQWpwUPv/E7c8zW6ePluOrTCBLeOqC4hp8F5qOd6zHp2hJ+fy51LXZjRpFpEq7Zv3huoN7V+6aTEr320SKgUF41gn15MdCnqfikYXLsDM7Ty6Y//YsWoDJ21D5tX9a0t/N4csyFvW/6yPOXKUgK9N7Ok8lnFvSuyih6cDeQ6rAbJvXQfaAyzDexaXvdfY8vWPq5CcMqzjxuNG753km45v7Z5kxdYAO5unwCIM4ME5H0GPPAicVtK+IeMKh0CG48eQZ65fc2SP72xsVdPlpbKnVTXCDD+V+nOJ/fZQVxemjD3/d7Aso8NZDE8oiUQ4x+gzMs3lDiCupRQqWWZMRxcpWc0Yvra2KNXsJHBQkb9o7mToRyBprgifIq8cfeCGW9hljb457D0B5A60M4vsNM7LYB9RQ+aRBPzpTjj7judu7CXVlNRuac16WLt9KbEddvYVt0BZZUp455okqOvcq9gCIIk3vNP3LNAISgLwkg1GMZ2pVs8Y6bhGRgFV7NvG7nz0whuta9YVsZvghRO+hd1rpxe+O/LvvFkxX/mPEN/PALRyE3GsJ1v34WW+o79WWRoI5vkzK9EOx0QW5O7oiygwtvPGkmRpeNxZvrF+L+Bfd1ZYQTrxCtXbUiecHNLfKOBFVB191FA8LGY2z/IrqrRqV7IahlcEMj002eam1GbWk1ukNbH80FsmL6kdzE4hEmvtW+p5XyD1aFtusN3PvzAOwZ0S2rp5DZ1AX9Jj5psHw3Xg8aobsSA1s5n6GfAapzbixuPpbVgF7Oizh++lS8cHOCmRzA+UoRh4xjLMiBK2K3QI1KBHdtRdtlF5pw4T2Bjg7kc1L9VGhHplDhzOIzPcOZeDe2X3aBPDPVmJVZeGrag534C6++u67lB3c/kfvAVWfhO6eMx9jBZZj6m2fx0jtrxbEmvZ7Wn0f5qJHGmcD81D3W1K/+em443+5NTr6ibjlumD0N25rrdvNotNgqnmo4apexoehunXykv47auyQVmYAyYaShpHuCuHDGsBwh90LfNxqIWS9n1JZEEapATj+Nxd3uz8SqYaQ+D7MjIwPtNHvBOsbjTTzVnsZL4cyID7QdcBkV77ZPQYpuPS7gDuLwX+d90uEh5kWgXYF3Tiz+6aeSg4/G5Klfxrxb3oDtbCax/l5y3I6bmev1+KEFbFMk2En+lQWIvpmZnq1iG+Ba303uGcfOjKB6AT4SeekFJEG/jy2ee3KQavT0kzBk0hMvxDSU8x6Z+9InXlo2/vxZf8MvLz0Tx4wZiCduOQ/iSPP72W9iwdJabN7eSJpgnldaUphz/hqt/xCRAH3gggN7DBZHfvXOxrcx7emrsbJuxW6f7ArVtq2Ajh4f3eGUkLqjfqRDtVa0blmLyqnjELc9O4TS69G0uRYFfY5hq4d74ra4j4aeSwuhFSivGmL89MUrTuq16rkIOc2wwwebQA6bXCQWkwCJnEBgvri/NlMcZH/tlcZTXbkarTHPB1o8nxCTAAtJtFBsqK9y30cLxf1Xb61DSmzz2lLxBpM4wtInsnwvSg/vc+6f7TK9kkDEeOAFkfwPrHM3N9FGbth8RKwR7P+KNFVC9M9420iK9EdyWksoWcSN37arX+QcDQu03mQCbCQ5hbKON2XhLkNbyzPGCy8NyN0mDR8GKzSBdbzgBQnEceML8OJtazrtuwS5wJnABR9m1ka5DezzmwhtXIKah7z1MZlgnEAkFdG8bUtX8xFK9/rUszmmv5DB9TOeeBrHJlUuCRO1rJvZ/tkcpmM83eJe85QYPC9KMUr2Kxpv4szlCFk89uJ6uVHX2pWFqCUS1ODk002A1HETUXFMC9vbyHYONOhnkQltb1mKkshRXAtR+5pNGHfaM9tWpc/lsEEmMk/CijX3m+XWIc79IN6EiVBjMw61Hf0LJ6LftfUBHV3ZxjM6ExA3SSkproAV057UGuP/veB9nHrt7zHrf07GyUcOxylHjcCUCcNR39iK2i07uL89iadXUe7FnM0fwYs4Ui2xFjy57AncO/8ebG7cM9O1pKdoT1Ftyy7ipKW4qPiEm5DJDlD5owLWvAe29qidiz+ioEzizc/14r4NcFZjD2N89f+aJAii75THzmPTM3mvn9kmWrnIkWiwyGxInLElOp/ainDoFG7eIHdp5OaMIdf6Hz7jBq9raifyQ2cTYSkuO5KEcAqf7evuZjAO+bg8q5rtBtvixrYu4+CPN0huymuXY38ZlVefi5rba3edKdHVnEnJn54P+vOYu/KtgOqUTlUlMah2ZnITncryxUbGly64EjWnLjf+7okuSUYWbV3EPn0uGX2nLYkAewETr/w6Xrpzs7k2cdogRNSNXmSfW5IKUGGbtrUJFVX3ob3pzqRIa1w/ne9Dx37EskPZvr9HOZyQRZ2wfw3n7zKTuDK/rZAIEUktvbsWrf0615+VS2RUKY6g1ZOYO/O35vv4C+9CQe8rWeh6JAOd9LHo3T4SOlLOPXOPueRa7YiETuA8caPad3JMpxkXVX9CYCvH+OvnWiTU+makuZhrEkNVToFvLYs+yLWbZlx1vD0AABLLSURBVCQkjS0oiUqI6wUcYp6JVIP1CJ/5v141m/NiCVN81SSytKwqlj3X+PUrf30kVsPi3lLIR7o6fhSJ8DOs/3ZAdPcjazIB4VTJPWj9CyZzKPKXrt6Mc2/+C048Yigu+dKxOPKg/igtyjWfAOSaFrSuZz/n3vTs9MJ/v/fkCU4809MD1bQkv7Yh0JdwavOY+wNROe0HyZ8uxdS54UfQJqkQHQme8fVUCV7oWFeovvomCmISF/x3Y9FV6ueeiJsUx2WzjONzxwa4t03usI6TndIXTdSTvJXGTunGApaWqLD7jMeg0ZFFDdLb/ZMHm5TZM3Bpo+8n2io0vghp02D6cRw3ohDNTqzf5NJ6WkNSQvM2xT2oHHUWtKSXwn/w0oyUD7UgJJRE5U3wywefJYEoz/gBHwkQxOmsTycgEpUAlhmGcECJ7/v4pE3AxLqLCmCQTtbjekTy5MZNJoOMdiTJx6UBvThBlAQBuZHVaXxKL276M/zrAd8Pazly1neRe0wdkcbG3Phbye8L729me/eRwElsgJ9KTaQsydSjJceht36W2uFl4gmJzeN0D8lMdpltJs7RkwqfJMH5UcpgmZgbM6EhY9/yJOIEgeccmEhEj8AoPY+EtUeKwJHI27rdnN5Y9kOsf4onlwQGI4FDXgTk4Z0+U6UbJsYhU1AqlRlkXf1b6F80l9dOkZ/Nre2gKI/Zr63AiP6lRPYBOHz4AcZQJ9DQ3I4tO5peuvLciu/1LIys/OfSfzyO7sFybEhzlhnd4f757OD5qb6SEo7FY4g2WNC5/QPXJXKJ4q+JQZ9kxDXvukgHFZzYGlLoangRVwISevggN4yIbtTZ1CGBZzaivbENucWpUDsTHplXz31dEnimS64gksHEgAg5i0TiV9DxXBMN17L1DW74vJTlPgkixUhGGtmIRwbW+VAvxVIn56ySfgk4mWUTRlZRW87ijS8gTCmkctr/AeHbULOkhdtUuNmEQP/f5ab7Pduo41gbEGupQyQn/YhH6w85KPFqGhewfEsc3UgzBoRk/sb77Yl68DDrPMAyInaKlONzIG70SoqDkmYckMwsiUQPy1jvepOlRalbiGh+ohByKss6Byr8T3L+0hQRJkJ0dd5s6cHJcrI2iYi7BMxbsY1EsDE4eXwu1TKV8ghzdZwIKNFsJ6YIKO5C3L2Ha0dJxB2KDQ0vY0CRRKpR0pS5TzKDZ0y4qZfVp6PzmDbz7UXW/ZHPCGTRoeQWJxOxQz/2kDwZsrvam0+1xuuLiT7caLLnWklddj6vS8q2lR72GX3R6fp4qiPIUQRqvO8ShdOv6jccwEkIcC45T19CDi+fh3ZtYfCsP9TswIm36DH5Qw8AupFC3kvRFDDJp4m4ncFKQ+XjPctIM4OnCgvZ1tkUAddS/TiHE/RowNBk+ZFkKeLnundg7vIZRuyddN2r3Ghzk+mdJMwzUijzF9QXV1PXzSWXCBrUNrChAjFfJ68p9R1uJoqIah7ijpdnr/IqOZIL9nUrF/uLmDfzVWNgi5hUR77Pw24MTE2bn6BIepOfw64s8EypI/rdVJMUpXzUb3n/zEDNxYiRGASzmci5vtYHBDh9Lcd9OmpmvoPjqkYRNRen9TmnRHKlnRlYmoXkK5dgTnU999tLVJGmeFKNgb5wwn3IuST7a64/VxLueRnmzPBSN1dOP4DP/z94Md8iCpGwOJKaK2ig6Tz1txipdEAxlVRPcaQf6xw3JC+9jMSUxylh2HnJa5YSI7Rw5OCanudHFM6jrvyEwYcV+AX31A62MSVZzmS4uWW2N5aqJ1L1TUjeHWiM3QZsp7rYL4wC57pAX1uMzp8j+fmSk78OjnUWXrw53UBbPrWefTk59Uz1M+5vky3Hm6R4u3Cd7jgBOKipSelCbaHnoLuVCKKPGHskhZSrdbfSGmuhcNX+IhljjAoiV5PhkFpPS34kQ6ogj2WNMlHHqZaepY7mW5t1PVRQStAiCkmoqp+iSAwpIlL7um1MrTaJCFOdIsWN6FQCBMCIUrYjce39Av1bDisiHDC4QCJ6ip3gXoTC/0H5tIlsX2LJg/WeQ9/3vTxmcbexQ0jmBi9hYicg454TnsXVKufYr2VfXkAQGUwCBhOHIJujJDD+J3ZJWZTriJrTM3DledREvLz9WqcbL0VysUOScKMocO0N1FR7SRE9b7qApZ/EKqTFiBRUH7cj1j4/+SvuipEt/WhLOGia6qU7P5mwo5JtNzA+itt2bHNamXBIsvYODVypI2MQo2jqaE0SkDTHuc/dV5LXJJGGqDAKP+ODXvCy3pobBYF6EmbrSxrniF4eTNwgSSx+beLxZb1C7SJBBg2xK0j6hMgFDaHP7YLkAh4nt/163O3xpGHS28h2RGKy95hCqkt4pXonKqoo1ppY40zYc4ii1BltbmRxrrJ6dOdRbDy1QL0buJFyg+JyI7a3zcBbd3Z2kC9ppsNJ7u/qAGGyhqdZZJX1tmcVDejllpui4hE93D/+Stx3KcJEEVFBz0IHLWGNXNUn0PkWbvKNFFO/yim4kn0QUWxAQEwfxiddjphdhZTKAJPA4DGfyITivdjfnECb7u6dKairzzc5228jYbwbpbEzSKQeSYnHsgfcvn4aqMTzdj3rdcMHmrNdFTA2Jqz1kY55DEw8vZ3G+cw1H8RirdWYANndQUQWYhxAAM5ffV4qHbSluB6Bo0vIcWlC7wX8hA6dH9nE20hIQgMDfXFSeevhpZnWjuQBDK7fc4hFdyLsBAnuCuRFd8Bp+SalsCsgmWrFJpSUZNRgXruCaoucUByarCWW9Hjc25OThhUhmNxFYQsaN6eMqRGTMKR/am7UB5Tm+vjZihP979wOIXkUUi03mbxsPniT1O5EuezFGUvQWu16hOGE/oaQI7rFoAzbyIvoiKUR6626IbpzP6bOFNsLLUSclDFG6zoUWsWYfF26q60bbxW33aRgJgaUuErlnJMsIlCJVMcU8xU5L7lXqls5Jg30sdPW8HoBaaYgYpDSr0dIDeuQtKOeiyYbNygptRp9OubWYX7oYhzVVoKoOoYb/H4kUyxRDAxZ6Y43Sqf0yXB4dIdwno3oCo6d1h+2m0/9fwPCeYK8ksmtIG2jQb8jVlmPYCRzmH0Fx09/EpuXvYveYy3U1kl2l1Fpy6T10sD4ByJ45u+aLLdxPyuMP+fqCBx3dSHm5zWhf+xUc0KRMhQuhh1/j4sr40xIAT3QS97qgpfNOJSxvSTWr4Fz8g/+PD7VIcU1jndO8FS+cPOAaqo2ondTKSaSUIXbRnHzUv+V7L1J2MH+PoBwa4S0pSRAwGImoeX4oeS+4ctw7I4bEco9kkj/K4PkXt8GYUfUQr4ekfayoxD8LDy2pOAqDtgLlqRJZMqS9QkSeZdz9YFv9PP2nFYnoHLaJNTufA3Rnhp2Y9hnbqlwcHMqEmpNPV7ApJCyMhehldo1MeP86vXk6qIPXJVpM1YkLtwukml5iu1xpd1ULnCrhQgYTkkESg1HKPK6KZr2IOuvaaK5kuSFOuVtpFVhQDfz3iBiWSs5wbGk9Vep77KnklFEfpekte9SLLXJccSanLym32SdEem+CUSqvN5ncgPcgAqHyGBLXnFxmy0NDHIuUWZgmr7o+imezX2TMyDF2VzVuTOFcM2obEB7MlxrHTezcDA555fNkOinpH7+HcLxOYjbwvUTTlOSg/w/6DWK1yiy9y+iBKCpPwaMt0oFEd1KIrqXmmoVYtYazpQkBvG4tOSEtyP/4Lgll97nUy6l4pHn3kYNrNbL9QY/U44gvP4DdV15gYO4Ah+WbB/qUS4D58S5PNUfOX9WnXv3hd2RHV4xdhh09A0yCTkT64F0Ubmdnzswd9mr5uUKdpD7sv3yUWLsuh2VXD+dvxliNXd1WaqUetkE1vR36gNtkovb9xE/3uX3DzmGPgGiuSlNIrOMsVUFfm9FrHUtQvniAv05f6wHmuPB/sXLoNs5+1HxbKREYqU8zeR41GY/K6qW8JlPe92zbU/0yhS003lYmXJ/b8TMzGCHCrlj9O6MSbs+oNFxVQphbXtYmr5qHCvEZdGceQc/YmhJWedNzraAXqn0oYF7OyFpkhpC/4H4cgee5hm0dA8kEycacIz45Wo7bbNrScsskkLwXBMrud++A8kyK3nfFCQX+peROIvVWhD6HsNYgx5othVwf1V2wJIrfgCdc3Rx5oCcu1NSswxyy5n6BKhk3n4R7e5AXfjveP7WOorO13hnuInHmPfCHevXOdhs1uQ4NAlEPPiWlaDa12acbMzRnRain3gltqCMvIrq6+xPIpvsNs791Ziz/BmjtyvnBl57CanX8wwzxkok0x6LIewxqJapXnkExHGxpOjOPfy08oljEnLg5aTvi2TWVeNn8AEkP31d6A6DfJY1FEGkE+5riRu3IYjf8Nfv3CTRAu87kipNjjbV35BIl+atZSX//pCf0zimFHPr6FKrfSKfgDgZhvEx0NcBSB2HCiFUkNOHo/mso429SuEZw5zMfXOcWm6eCYxI6DdhZJAU0geHHKKLVyFFlvG2WEm/tudm1Duuix4KKmNJgky5JR6OpXQryR2usAh6D69/1moBvOSJb/hXthlxNtVya/Ke0lvQ7K4zDjOVV32fuPohEeI0LqZs9HWcwH+wrFiwf+i1LYSNqozlDmV//DbklTskBpYl+b/f9ImFJObfzu8L+BGJYLCvm8tdQdbZiDs/M55ilVNbU22J1TXNmhxK9VWSTurdvGRSP2d0YS/PWwSeMUv054XcQPeRwz6PJX6AhrzJtWL66RCfCM9LLN/vW605UtSupOT250/Vk8Sk1Akpm5pb6ts+Z5EccBXXkUjZP+C8TvQNYt54JWU24g+hZlbqjT01s1Zj4pVnm3N4bU4BBhniKbYXRWlIPNliLY+ZjS/vj5NEikgSro2I1+3sdBoki65WXbkQy8sv5Jh1HuLtz5p8f6lxyXp59UxKffPGmF6GSGgMQeLYUpJuQj3Pa3dj/gwvY3CT/Q8Uxu70jJ1a3GHjpo/mxEq9mURm3SFeQFOtU9YiM0uyZ5XlSc81M1/HcVWnIUTCqCEBSsVmLrW0B6+N2h1PkcuLo853TfJQSQ8ufbOsdR61qph2Iy9ej0xAuJmrTzJHPZ3B5KknkgNJ7u2c3bYTdz8/+sw/HcZ1nIWMQW9QTQ0jU69K7hCL3BWs2OhgfIlldCcB0WuCEUqiN3d2r7zqZD7zAJMM0bEaKIo6RBBOsry6BwkL9F+Je18zutvwvqH0NuTlgn1TnCTx+mWhvgXtJcinKGw059jOtOgxyUlee7DdaV/FRbW2rvN7u4CEpNYXwc0vJPIVIRxrQ1ztMGfGXRrw5J3tkoM8J4q2sPhA1ntEbzfP7Wr+0vpBDtQW8/TkaHiH8arcHciRmBUuYVmFRrcVr66o36XPac/dyOc+1sVc7Gaf7DbWu2Ose4brF6wvOfrjlkiUjhm32GkS82ja7Bg/38WeCYI4z0TsvC6fLWGuTmEeIrE459zcV6YzFc7fkX6O2jUIhdDWSeYlcJ2BCRvNe9q4+3UNlAowZcwZf/omyda3kSFQunr73eZVR3wsSSe8lxhSZ9QHe2eqhoPlGG6ckH5cdz1c+0y8ePNCZCELn2IIeRSreGDGNRR15Nb45i7vG7Fq+q+JIN57oDsDOZe220WaGINuANWOVcMjfUvab410G9EjbY3xFdXbdmZcIZQvhGq4rw8XIPE2EM/tUSiwGDkuyyJ5Fv4bIIQBPXMzTyEFz5BlteweYeRFCBHnfSLF6C4aieeVbt3hap1rqW7Y4uCWh8N5NRGlup0vTodKXkf1tgspDWQW29u8+Rnk9TkZtjuZetbIgOFnJZH/RTQ7T/lRZlnIwqceQnBjhdy4RRnb3MWBP1q8+wgUeRFCRdUfKGvP7NSBhsQi0nv9VqUQRrfAKlFytPUR0kIq8Zpa342aEuhgfIXhe2clUvk8ls0+m4X/OrDgWqUI+kHvCbRek1k5PGocEzp/6rbc/J3yutI++JhAQ8VxP/YiwZ4geBbJs/DfCRYsLdbjnMyrqNpdXnfbGcwNyVn7Pzq9p9WqnOJGcTEtwscGOitmZ+EzCxaU7t+tGuaAPxPOJkcG6kF0HlG01Q43Hygxm/i4wNVvIAtZ+IyCuC72QXciyHQ3DGFb7cV+xFQ6UGEOhSTYtzuGuI8OJCgcoV6LLGThMwqC4IMyjDjzQW/JuKjnOPFr6F1043qldUl3vOL2BkjNmqGRfbNLFj6zIE7VvbpVQ7KydAecmGSoWNShjSUkLofjYwKtdZtS8ayOnoXPLAhHHdjNGh92q7y45yn9W9/JxG/DXcXfg/ExgVKWbmmya5GFLHxGIUT0+zsR8ZXMiqu4CbPrLsT14yZxgJd/XWKAJVRvjlbqY8oL7n64GqsbkYUsfCYB+P8P9O74HbhK+AAAAABJRU5ErkJggg=='
					    				} );
					                }
					                }				            
			        ],
				
				
				"columnDefs": [
					 {
			                "targets": [3],			               
			                "searchable": false,
			                "orderable": false
			            }
			        ]
			});			
			
			$("tr").not(':first').hover(
			  function () {
			    $(this).css("background","yellow");
			  }, 
			  function () {
			    $(this).css("background","");
			  }
			);			
		
			$(".loadPage").show();
			$(".loadingTable").css("display","none");
			$("#loadingSpinner").css("display","none");
		});

		</script>
   
  </head>

<body>
<div class="siteHeaderGreen">Job Categories</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Job Categories Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 
Below are the current job categories assigned in MemberServices. You can View, Add, Edit, or Delete any Category. To find a particular category, use the search or scroll through the list below. Each category has corresponding Roles and user members associated with them.
<br/><br/>
<div class="alert alert-danger"><b>NOTICE:</b> Please make sure you are aware that these categories may be linked to various MS Applications and User Roles. 
Removing them may remove user access or functionality in any application on the system. 
Make sure you are aware of the changes you make!</div>
<br/>
<div align="center">
<a class="btn btn-sm btn-primary" data-toggle="modal" data-target="#addCategoryModal"><i class="fas fa-plus"></i> Add New Category</a>

 &nbsp; 
 <a class="btn btn-sm btn-danger" href="/MemberServices/navigate.jsp"><i class="fas fa-undo-alt"></i> Back to Staff Room</a></div>
  <br/>
  <div style="text-align:center;padding:10px;">
  <b>REGULAR CATEGORIES:</b> <span class="regCategories"></span> &nbsp; | &nbsp; 
  <b style="color:Green;">NEW CATEGORIES:</b> <span class="newCategories"></span> &nbsp; | &nbsp; 
  <b style="color:Red;">OLD CATEGORIES:</b> <span class="oldCategories"></span> &nbsp; | &nbsp; 
  <b>TOTAL CATEGORIES:</b> <span class="totalCategories"></span>
  </div>
  
<table class="rolesTable table table-sm table-bordered table-striped responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">					
					<th width="10%">Category ID</th>
					<th width="30%">NAME</th>	
					<th width="50%">DESCRIPTION</th>											
					<th width="10%">OPTIONS</th>					
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${categories}" var='cat'>
					<tr>
					<td width="10%">${cat.personnelCategoryID}</td>
					<td width="30%">${cat.personnelCategoryName}</td>
					
					<c:choose>
					<c:when test="${cat.personnelCategoryDescription eq 'ORIGINAL CATEGORY'}">
					<td width="50%" style="background-color:rgba(255, 0, 0, 0.8);color:White;text-align:center;">${cat.personnelCategoryDescription}</td>
					<% oldCatNum++;%>
					</c:when>
					<c:when test="${cat.personnelCategoryDescription eq 'NEW CATEGORY'}">
					<td width="50%" style="background-color:rgba(0, 128, 0, 0.2);color:Green;text-align:center;">${cat.personnelCategoryDescription}</td>
					<% newCatNum++;%>
					</c:when>
					<c:otherwise>
					<td width="50%" style="text-align:center;">${cat.personnelCategoryDescription}</td>
					<% regCatNum++;%>
					</c:otherwise>
					</c:choose>		
					<td width="10%"> 
               			<a onclick="loadingData();" class="btn btn-primary btn-xs" href="viewCategory.html?cid=${cat.personnelCategoryID}">VIEW</a>                      
              			<a class="btn btn-danger btn-xs">DEL</a>
              		</td>
              		</tr>										
				</c:forEach>         

                   
            
            
 </tbody>
 </table>

 </div>
 		<br />
<div class="modal fade" id="addCategoryModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Add New Catgory</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <div class="container">
  		<div class="row" style="padding-top:5px;">
    		<div class="col-md-3 col-sm-12">
      			NAME
    		</div>
    		<div class="col-md-9 col-sm-12">
      			<input type="text" id='txtname' class="form-control">
      		</div>
  		</div>
  		<div class="row" style="padding-top:5px;">
    		<div class="col-md-3 col-sm-12">
      			DESCRIPTION
    		</div>
    		<div class="col-md-9 col-sm-12">
      			<input type="text" id='txtdescription' class="form-control" maxlength="150" value="NEW CATEGORY" readonly>
      		</div>
  		</div>
  		<div class="row" style="text-align: center;padding-top:5px;">
  			<div class="col-lg-12">
  			<br/>
    		 <div id="msgerradd" class="alert alert-danger" style="display:none;"></div>
    		 </div>
  		</div>
  		         
       
		</div>
      
      </div>
       
      
      <div class="modal-footer">
      <button type="button" class="btn btn-success btn-sm" onclick="addNewCategory()">Save</button>
      	<button type="button" class="btn btn-danger btn-sm" data-dismiss="modal">Close</button>
        
      </div>
    </div>
  </div>
</div>

<script>
$(".regCategories").text(<%=regCatNum%>);
$(".newCategories").text(<%=newCatNum%>);
$(".oldCategories").text(<%=oldCatNum%>);
$(".totalCategories").text(<%=newCatNum+oldCatNum+regCatNum%>);
</script>

 </body>
 </html>
         