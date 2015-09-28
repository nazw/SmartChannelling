$(function(){
	jQuery.ajaxSettings.traditional = true;
	$( "#hcpcCodeList" ).autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "hcpcCodesListAutoSearch.htm",
                dataType: "json",
                data: {term: request.term},
                success: function( data ) {
                    response( $.map( data, function( item ) {
                        return {value: item.hcpcCode,id: item.hcpcCodeId};
                    }));
                }});
        },
        minLength : 1,
        autoFocus:true,
        formatItem: function(row, i, total) {
            return row.value;
        },
        select : function(event, ui){
            $('#add_product_button_grey').hide();
            $('#add_product_button').show();                	
            $('#hcpcCodeId').val(ui.item.id);
            //$("#hcpcCodeDescription").val(ui.item.desc);
        }
    });
});

function validateProduct(){
    var buttonSrc = 'images/add_products.png';
    var productName = $('#supplierProductName').val();
    var hcpcCode =  $('#hcpcCodeList').val();
    var hcpcCodeId=$('#hcpcCodeId').val();
    if($('#imageId').attr('src') === buttonSrc){   
        if(productName == ""){
            alert('Product Name cannot be empty');
        }else if (hcpcCode == ""){
            alert('Hcpc Code cannot be empty');
        }else if (hcpcCode != ""){               
            if(hcpcCodeId==null  || hcpcCodeId==""){
                alert(hcpcCode + " is not a valid hcpc Code");
                return false;
            }else{
                return true;
            }
           
        }
        return false;   
    }
    
    return true;
}
    
    	

/*** This methods below will handle the Product Category Management ***/
   
var productCategoriesArray = new Array();
var removedSupplierOptionIdList = new Array();

var productCategoryDiv = '#product_categories';
var categoryNameInput = "#category_name_";
var addOptionButton = "#add_option_";
var addedOptionsRow = "#added_category_option_";
var categoryRow = "#category_";
var categoryNameTr = '#category_name_tr';

var CategoryOption ;
var ProductCategory ;


	//Constructor for ProductCategory Object
	function ProductCategory(categoryId,categoryName,categoryOptions){
		this.categoryId = categoryId;
		this.categoryName=categoryName;
		this.categoryOptions=categoryOptions;
	}

	//Constructor for CategoryOption Object
	function CategoryOption(optionId,optionName,supplierOptionId){
		this.optionId = optionId;
		this.optionName=optionName;
		this.supplierOptionId = supplierOptionId;
	}
   
 	//This Method adds white box with the text box to add the option category name in it .
   function addProductCategory(){
	   var categoryId = 0;
	   var categoryName,categoryOptions ;

	   productCategoriesArray.push(new ProductCategory(categoryId,categoryName,categoryOptions));
       iterateProductCategoriesArray();
   }

   //The method which creates the html Stirng , iterates it and appends it 
   function iterateProductCategoriesArray(){
       var htmlString ;
       $(productCategoryDiv).html('');

	   $.each(productCategoriesArray,function(index,productCategory){
		   var categoryName = productCategory.categoryName;
	
	       if(categoryName == undefined){
	           htmlString = 
	               ' <tr id="category_'+index+'">'
	               	+' <td class="product_options_text">'
	                  		+'<div class="option_white_box" >'
		                   		+'<table style="margin-top:6px;padding-top:5px;width: 665px;">'
		                   			+'<tr>'
		                   				+'<td style="width:110px"><input type="text" id="category_name_'+index+'" class="option_cat_textbox"  placeholder="Category Name" onkeyup="findMyCategories('+index+',this.value)"/></td>'
		                   				+'<td style="width:20px"><input type="button" class="confirm_icon" type="button" onclick="confirmCategory(\''+index+'\')"/></td>'
		                   				+'<td><a href="javascript:void(0);" class="product_links" onclick="removeCategoryByLink(\''+index+'\')">Remove Category</a></td>'
		                   				+'<td style="float: right"><a  id="add_option_'+index+'" href="javascript:void(0);" class="product_links" onclick="addOption(\''+index+'\')" style="display:none">+ Add Option</a></td>'
		                   			+'</tr>'
		                   			+'<tr>'
		                   				+'<table style="  margin-top:6px;width: 665px;margin-left:15px">'
		                   					+'<tr id="added_category_option_'+index+'"></tr>'
		                   				+'</table>'
		                   			+'</tr>'
		                   		+'</table>'
	               			+'</div>'
	               		+'</td>'
	               +'</tr>';
	       }else{
	           htmlString = 
	               ' <tr id="category_'+index+'">'
	               		+'<td class="product_options_text">'
	               			+'<div class="added_option_box" >'
	               				+'<table style="margin-top:6px;padding-top:5px;width: 665px;">'
	               					+'<tr id="category_name_tr'+index+'">'
	               						+'<td class="product_category_name" >'+categoryName+' : '
	               							+'<a href="javascript:void(0);" class="cat_links" onclick="editCategoryName(\''+categoryName+'\',\''+index+'\')">Edit Category Name</a>&nbsp;'
	               							+'<a href="javascript:void(0);" class="product_links" onclick="removeCategoryByLink(\''+index+'\')">Remove Category</a></td>'
	               						+'<td style="float: right;vertical-align:bottom"><a  id="add_option_'+index+'" href="javascript:void(0);" class="product_links" onclick="addOption(\''+index+'\')">+ Add option</a></td>'
	               					+'</tr>'
	               					+'<tr>'  
	               						+'<table style="  margin-top:6px;width: 665px;margin-left:15px">'
	               						+'<tr id="added_category_option_'+index+'"></tr>'
	               						+'</table>'
	               					+'</tr>'
	               				+'</table>'
	               			+'</div>'
	               		+'</td>'
	               +'</tr>';
	               
	           }
	           
	           $(productCategoryDiv).append(htmlString);
	           
	           iterateCategoryOptions(productCategory.categoryOptions,index,categoryName);
	       });
	   
	   }

	//This methods iterates over the options of the Category
	function iterateCategoryOptions(categoryOptions,categoryId,categoryName){
		$(addedOptionsRow+categoryId).html('');
		if(categoryOptions != undefined ){
	        $.each(categoryOptions,function(i,categoryOption){
	           
	            htmlString =
	                '<div class="option_value" >'
	                		+'<table>'
	                			+'<tr>'
	                				+'<td style="padding-left: 2px">'+categoryOption.optionName+'</td>'
	                				+'<td style="padding-left: 2px"><img  src="images/close_btn.png" onclick="removeOption(\''+categoryId+'\',\''+i+'\',\''+categoryName+'\')" style="cursor:pointer" /></td>'
	                			+'</tr>'
	                		+'</table>'
	                +'</div>';
	           
	            $(addedOptionsRow+categoryId).append(htmlString);
	           
	        });
	    }
	}

//This method creates the category visually but does not store the ProductCategory object in the Session.	
   function confirmCategory(categoryId){
	   
	 var categoryName = $(categoryNameInput+categoryId).val();
		
	 if(categoryName.split(" ").join("") != ""){
			 
		var categoryAtLocation = duplicateProductCategory(categoryName);
		
		if(categoryAtLocation == -1){
	       productCategoriesArray[categoryId].categoryName = categoryName;
	       iterateProductCategoriesArray();
	       addOption(categoryId);
		}else{
			$(categoryNameInput + categoryId).val('');
			alert("Category Already Exists !");
		}
	 }else{
		 alert("Category Name cannot be empty !");
     }
 
   }

 	var categoryOptionDiv = '#category_options_';
 	var optionNameString = '#option_name_';

 	//This method adds the Option Visually 
	function addOption(categoryId){
	   $(addOptionButton+categoryId).hide();
	   productCategoriesArray[categoryId].categoryName;
	
	   var optionHtmlString = 
	       '<div class="option_text " style="min-width:100px" id="category_options_'+categoryId+'">'
	       		+'<table>'
	           		+'<tr>'
	           			+'<td><input type="text" id="option_name_'+categoryId+'" class="option_value_textbox"  placeholder="Option" /></td>'
	           			+'<td style="width:20px"><input type="button" class="confirm_icon" type="button" onclick="confirmOption(\''+categoryId+'\')"/></td>'
	           			+'<td style="padding-left: 2px;cursor:pointer" onclick="removeInputForCategoryOption('+categoryId+');"><img src="images/close_btn.png"/></td>'
	         		+'</tr>'
	       		+'</table>'
	       +' </div>';
	     
	   $(addedOptionsRow + categoryId).append(optionHtmlString);
	}

	//This method creates the option visually and stores the ProductCategory object in the Session along with the CategoryOptions.
	function confirmOption(categoryId){
	
	 	var optionName = $(optionNameString + categoryId).val();
	   
		if(optionName.split(" ").join("") != ""){	
			
			if(duplicateCategoryOption(categoryId, optionName) == -1){
				//To a Option exist a category must ALWAYS exist.
				var productCategory = productCategoriesArray[categoryId];
				var categoryOptions = productCategory.categoryOptions;
	
				//The if statement will be executed if this is the first time a option is added.
				if(categoryOptions == undefined){
					categoryOptions = new Array();
				}
	
				categoryOptions.push(new CategoryOption(0,optionName,0));
	
				//We set the options to the Product Category object	
				productCategory.categoryOptions = categoryOptions;
	
				var productCategoryString = JSON.stringify(productCategory);
				
	        	$.post('addProductCategoryToSesion.htm', {productCategory:productCategoryString,removedOptionList:null}, function(data){
		           if(data != null){
		        	 	//remove the existing item at the location and replace it with the new item
	                   	productCategoriesArray[categoryId] = productCategory;
						iterateCategoryOptions(data,categoryId,productCategory.categoryName);
	
		            }
		           removeInputForCategoryOption(categoryId);
		        });
		        
			}else{
				alert("This option already exists");
				$(optionNameString + categoryId).val('');
			}
		}else{
			alert("Option Name cannot be blank or empty");
			
	 	}
	}

	//removes the inputBox for creating an option
	function removeInputForCategoryOption(categoryId){
	    $(categoryOptionDiv+categoryId+'').remove();
	    $(addOptionButton+categoryId).show();
	}

	//This Method removes the entire ProductCategory for the Product when the "Remove Cateogry link is clicked".
	function removeCategoryByLink(categoryId){
		
		var productCategory =  productCategoriesArray[categoryId];
		
		if(productCategory.categoryOptions != undefined){
			$.post('removeProductCategoryFromSession.htm',{categoryName:productCategory.categoryName},function(productCategories){
	
	            productCategoriesArray.splice(categoryId, 1); 
	            iterateProductCategoriesArray();
	        });
		}else{
			productCategoriesArray.splice(categoryId, 1);
			iterateProductCategoriesArray(); 
		}
		    
	}

	//This method removes the CategoryOption from the ProductCategory by changing he state of the value to false
	function removeOption(categoryId,optionId){
		var productCategory = productCategoriesArray[categoryId];
		var categoryOptions = productCategory.categoryOptions;
		
		
		//Adds the removed supplierOptionId to the list.To be used on an update
		if(categoryOptions[optionId].supplierOptionId != 0){
			removedSupplierOptionIdList.push(categoryOptions[optionId].supplierOptionId);
		}
		//Remove the CategoryOption from the List
		categoryOptions.splice(optionId,1);
		productCategory.categoryOptions = categoryOptions;
		
		if(removedSupplierOptionIdList.length > 0){
			$.post('addProductCategoryToSesion.htm', {productCategory:JSON.stringify(productCategory),removedOptionList:removedSupplierOptionIdList}, function(data){
				iterateCategoryOptions(data, categoryId,productCategory.categoryName);
		    });
		}else{
			$.post('addProductCategoryToSesion.htm', {productCategory:JSON.stringify(productCategory),removedOptionList:null}, function(data){
				iterateCategoryOptions(data, categoryId,productCategory.categoryName);
		    });
		}
		
	    
	}

	//Edit the category Name of the ProdcutCategory
	function editCategoryName(categoryName,index){
	  
		
	 var htmlString = 
	        '<td style="width:110px"><input type="text" id="category_name_'+index+'" class="option_cat_textbox"  placeholder="Category Name" value="'+categoryName+'" /></td>'
	        +'<td style="width:20px"><input type="button" class="confirm_icon" type="button" onclick="editNameAndConfirmCategory(\''+index+'\',\''+categoryName+'\')"/></td>'
	        +'<td><a href="javascript:void(0);" class="product_links" onclick="removeCategoryByLink(\''+index+'\')">Remove Category</a></td>'
	        +'<td style="float: right"><a  id="add_option_'+index+'" href="javascript:void(0);" class="product_links" onclick="addOption(\''+index+'\')" style="display:none">+ Add Option</a></td>';
	    
	    $(categoryNameTr + index).html(htmlString);
	    
	}

	//This method is fired when the user edits a category's name and then click confirm
	function editNameAndConfirmCategory(categoryId,oldCategoryName){
	
		var categoryName =  $(categoryNameInput+categoryId).val();
	    if(categoryName.split(" ").join("") != ""){
	
	        if($.trim(categoryName.toLowerCase()) != $.trim(oldCategoryName.toLowerCase())){
	            
	        	 var productCategory = productCategoriesArray[categoryId];
	        	 productCategory.categoryId = 0;

	        	 if(productCategory.categoryOptions != undefined){
	        		 $.each(productCategory.categoryOptions,function(idx,productOption){
	        			 productOption.optionId = 0;
	        		 });
	        	 }
	        	 
	        	 productCategory.categoryName = categoryName;
	        	
	            $.getJSON("renameCategoryNameInSession.htm", {oldName:oldCategoryName,productCategory:JSON.stringify(productCategory)});
	           
	            
	            //After the rename this category is treated as a whole new entity.
	            
	        }
	    }
	    iterateProductCategoriesArray();
	}

	var categories = new Array();
	
	function findMyCategories(categoryId,textValue){
	    var htmlString = "";
	    var textLength = textValue.length;
	    $(addedOptionsRow+categoryId).html("");
	    categories.length = 0;
	    
	    if(textLength >= 2){
	       
	        $.getJSON("searchExistingCategoriesByUser.htm", {term:textValue}, function(data){
	            var x = 0;
	
	            $.each(data[0],function(categoryName,productCategory){
	              
	                var selectedCategoryId = categories.push(productCategory);
	                selectedCategoryId = selectedCategoryId -1;
	                htmlString = 
	                    '<table><tr>'
	                    	+ '<td style="width:98px;font-size:13px" >'+categoryName  +':</td>'
	                    	+ '<td><input type="button" class="confirm_icon" type="button" onclick="selectCategoryFromList(\''+selectedCategoryId+'\',\''+categoryId+'\')"/></td>'
	                    	+ '<td id="addedOptionsSpan'+categoryId+""+x+'"></td>'
	                    + '<tr></table>';
	               
	                
	                $(addedOptionsRow+categoryId).append(htmlString);
	
	                 $.each(productCategory.categoryOptions,function(id,val){
	                    htmlString ='<div class="option_value" >'
	                        +'<table>'
	                        +'<tr>'
	                        +'<td style="padding-left: 2px">'+val.optionName+'</td>'
	                        +'</tr>'
	                        +'</table>'
	                        +'</div>';
	
	                    $('#addedOptionsSpan'+categoryId+x).append(htmlString);
	                });
	                x++;
	            });
	        });
	    }
	}

	function selectCategoryFromList(selectedCategoryId,categoryId){
		
	    var productCategory = categories[selectedCategoryId];
	    var overwrite = true;
	    
	    var existingCategoryId = duplicateProductCategory(productCategory.categoryName);
	    if(existingCategoryId !== -1){
	    	overwrite = confirm("A Product Category with this name already exists ,\nDo you wish to overwrite it ?");
	    	categoryId = existingCategoryId;
	    }
	    
	    if(overwrite){
	       	 $.post('addProductCategoryToSesion.htm', {productCategory:JSON.stringify(productCategory),removedOptionList:null}, function(data){
	            //remove the existing item at the location and replace it with the new item
				productCategoriesArray[categoryId] = productCategory;
	   			iterateProductCategoriesArray();
	            removeInputForCategoryOption(categoryId);
	        });
	    }else{
	    	iterateProductCategoriesArray();
	    }
	}

	//checks if the categoryoption already exists for a given category
	function duplicateCategoryOption(categoryId,optionName){
	
		var productCategory = productCategoriesArray[categoryId];
	
		if(productCategory.categoryOptions != undefined){
	
			for (var i=0;i<productCategory.categoryOptions.length;i++){
				if($.trim(optionName).toLowerCase() == $.trim(productCategory.categoryOptions[i].optionName).toLowerCase()){
					
					return i;
				}
			}
		}
		return -1;
	}

	//checks if the categoryoption already exists for the product
	function duplicateProductCategory(potentialCategoryName){
		
		var categoryName;
		
		if(productCategoriesArray != undefined){
	
			for (var i=0;i<productCategoriesArray.length;i++){
				
				categoryName = productCategoriesArray[i].categoryName;
				
				if(categoryName !== undefined){
					if($.trim(categoryName).toLowerCase() == $.trim(potentialCategoryName).toLowerCase()){	
						return i;
					}	
				}
				
			}
		}
		return -1;
	}