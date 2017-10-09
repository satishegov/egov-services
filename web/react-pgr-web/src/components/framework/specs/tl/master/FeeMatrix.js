var dat = {
	"tl.create": {
		"numCols": 12/3,
		"url": "/tl-masters/feematrix/v1/_create",
		"tenantIdRequired": true,
		"useTimestamp": true,
		"objectName": "feeMatrices",
		"groups": [
			{
				"label": "tl.create.groups.feematrixtype.title",
				"name": "createFeeMatrixType",
				"fields": [
            {
              "name": "applicationType",
              "jsonPath": "feeMatrices[0].applicationType",
              "label": "tl.create.groups.feematrixtype.applicationtype",
              "pattern": "",
              "type": "singleValueList",
              "url": "",
              "isRequired": false,
              "isDisabled": false,
              "requiredErrMsg": "",
              "patternErrMsg": "",
							"defaultValue": [
	          {
	            "key": "NEW",
	            "value": "NEW"
	          },
	          {
	            "key": "RENEW",
	            "value": "RENEW"
	          }
	            ]
            },
            {
              "name": "businessNature",
              "jsonPath": "feeMatrices[0].businessNature",
              "label": "tl.create.groups.feematrixtype.natureofbusiness",
              "pattern": "",
              "type": "singleValueList",
              "url": "",
              "isRequired": false,
              "isDisabled": false,
              "requiredErrMsg": "",
              "patternErrMsg": "",
							"defaultValue": [
								{
			            "key": null,
			            "value": "--Please Select--"
			          },
	          {
	            "key": "PERMANENT",
	            "value": "PERMANENT"
	          },
	          {
	            "key": "TEMPORARY",
	            "value": "TEMPORARY"
	          }
	            ]
            },
            {
              "name": "categoryId",
              "jsonPath": "feeMatrices[0].category",
              "label": "tl.create.groups.feematrixtype.licensecategory",
              "pattern": "",
              "type": "singleValueList",
              "url": "/tl-masters/category/v1/_search?tenantId=default&active=true&type=category|$..code|$..name",
              "isRequired": true,
              "isDisabled": false,
              "requiredErrMsg": "",
              "patternErrMsg": "",
							"depedants": [{
	              "jsonPath": "feeMatrices[0].subCategory",
	              "type": "dropDown",
	              "pattern": "/tl-masters/category/v1/_search?tenantId=default&active=true&type=subcategory&category={feeMatrices[0].category}|$.categories.*.code|$.categories.*.name"
	            }]
            },
            {
              "name": "subCategoryId",
              "jsonPath": "feeMatrices[0].subCategory",
              "label": "tl.create.groups.feematrixtype.subcategory",
              "pattern": "",
              "type": "singleValueList",
              "url": "",
              "isRequired": true,
              "isDisabled": false,
              "requiredErrMsg": "",
              "patternErrMsg": "",
							"depedants": [
	              {
	              "jsonPath": "feeMatrices[0].uomName",
	              "type": "text",
	              "isRequired": false,
	              "isDisabled": true,
	              "pattern": ""
	            },
		          {
		          "jsonPath": "feeMatrices[0].rateType",
		          "type": "text",
		          "isRequired": false,
		          "isDisabled": true,
		          "pattern": ""
						},
						{
							"jsonPath": "feeMatrices[0].feeType",
							"type": "dropDown",
							"pattern": "/tl-masters/category/v1/_search?tenantId=default&active=true&type=subcategory&codes={feeMatrices[0].subCategory}|$..feeType|$..feeType"
						}
	          ]
            },

            {
              "name": "feeType",
              "jsonPath": "feeMatrices[0].feeType",
              "label": "tl.create.groups.feematrixtype.feetype",
              "pattern": "",
              "type": "singleValueList",
              "url": "",
              "isRequired": true,
              "isDisabled": false,
              "requiredErrMsg": "",
              "patternErrMsg": "",
						// 	"defaultValue": [
	          // {
	          //   "key": "LICENSE",
	          //   "value": "LICENSE"
	          // }
	          //   ]
            },
						{
							"name": "uomName",
							"jsonPath": "feeMatrices[0].uomName",
							"label": "tl.create.groups.feematrixtype.unitofmeasurement",
							"pattern": "",
							"type": "text",
							"isRequired": false,
							"isDisabled": true,
							"requiredErrMsg": "",
							"patternErrMsg": ""
						},
						{
							"name": "ratetype",
							"jsonPath": "feeMatrices[0].rateType",
							"label": "tl.create.groups.feematrixtype.ratetype",
							"pattern": "",
							"type": "text",
							"isRequired": false,
							"isDisabled": true,
							"requiredErrMsg": "",
							"patternErrMsg": ""
						},
            {
              "name": "financialYear",
              "jsonPath": "feeMatrices[0].financialYearRange",
              "label": "tl.create.groups.feematrixtype.effectivefinancialyear",
              "pattern": "",
              "type": "singleValueList",
              "url": "/egf-masters/financialyears/_search?tenantId=default|$..finYearRange|$..finYearRange",
              "isRequired": true,
              "isDisabled": false,
              "requiredErrMsg": "",
              "patternErrMsg": ""
            },
						{
							"name": "effectiveFrom",
							"jsonPath": "feeMatrices[0].effectiveFrom",
							"label": "tl.create.groups.feematrixtype.effectiveFrom",
							"pattern": "",
							"type": "text",
							"isRequired": false,
							"isDisabled": true,
							"requiredErrMsg": "",
							"patternErrMsg": "",
							"isHidden": true
						},
						{
							"name": "effectiveTo",
							"jsonPath": "feeMatrices[0].effectiveTo",
							"label": "tl.create.groups.feematrixtype.effectiveTo",
							"pattern": "",
							"type": "text",
							"isRequired": false,
							"isDisabled": true,
							"requiredErrMsg": "",
							"patternErrMsg": "",
							"defaultValue": null,
							"isHidden": true
						}
				]
			},
		]
	},

  "tl.search": {
		"numCols": 12/2,
		"url": "/tl-masters/feematrix/v1/_search",
		"tenantIdRequired": true,
		"useTimestamp": true,
		"objectName": "feeMatrices",
		"groups": [
			{
				"label": "tl.search.groups.feematrixtype.title",
				"name": "feeMatrices",
				"fields": [
					{
						"name": "applicationType",
						"jsonPath": "applicationType",
						"label": "tl.create.groups.feematrixtype.applicationtype",
						"pattern": "",
						"type": "singleValueList",
						"url": "",
						"isRequired": false,
						"isDisabled": false,
						"requiredErrMsg": "",
						"patternErrMsg": "",
						"defaultValue": [
					{
						"key": "NEW",
						"value": "NEW"
					},
					{
						"key": "RENEW",
						"value": "RENEW"
					}
						]
					},
					{
						"name": "businessNature",
						"jsonPath": "businessNature",
						"label": "tl.create.groups.feematrixtype.natureofbusiness",
						"pattern": "",
						"type": "singleValueList",
						"url": "",
						"isRequired": false,
						"isDisabled": false,
						"requiredErrMsg": "",
						"patternErrMsg": "",
						"defaultValue": [
					{
						"key": "PERMANENT",
						"value": "PERMANENT"
					},
					{
						"key": "TEMPORARY",
						"value": "TEMPORARY"
					}
						]
					},
					{
						"name": "categoryId",
						"jsonPath": "category",
						"label": "tl.search.groups.feematrixtype.licensecategory",
						"pattern": "",
						"type": "singleValueList",
						"url": "/tl-masters/category/v1/_search?tenantId=default&active=true&type=category|$..code|$..name",
						"isRequired": false,
						"isDisabled": false,
						"requiredErrMsg": "",
						"patternErrMsg": "",
						"depedants": [{
							"jsonPath": "subCategory",
							"type": "dropDown",
							"pattern": "/tl-masters/category/v1/_search?tenantId=default&active=true&type=subcategory&category={category}|$.categories.*.code|$.categories.*.name"
						}]
					},
					{
						"name": "subCategoryId",
						"jsonPath": "subCategory",
						"label": "tl.search.groups.feematrixtype.subcategory",
						"pattern": "",
						"type": "singleValueList",
						"url": "",
						"isRequired": false,
						"isDisabled": false,
						"requiredErrMsg": "",
						"patternErrMsg": "",
						"depedants": [
							{
								"jsonPath": "feeType",
								"type": "dropDown",
								"pattern": "/tl-masters/category/v1/_search?tenantId=default&active=true&type=subcategory&codes={subCategory}|$..feeType|$..feeType"
							}
						]
						},
						{
              "name": "financialYear",
              "jsonPath": "financialYear",
              "label": "tl.search.groups.feematrixtype.effectivefinancialyear",
              "pattern": "",
              "type": "singleValueList",
              "url": "/egf-masters/financialyears/_search?tenantId=default|$..finYearRange|$..finYearRange",
              "isRequired": false,
              "isDisabled": false,
              "requiredErrMsg": "",
              "patternErrMsg": ""
            },
						{
              "name": "feeType",
              "jsonPath": "feeType",
              "label": "tl.create.groups.feematrixtype.feetype",
              "pattern": "",
              "type": "singleValueList",
              "url": "",
              "isRequired": false,
              "isDisabled": false,
              "requiredErrMsg": "",
              "patternErrMsg": "",
						// 	"defaultValue": [
	          // {
	          //   "key": "LICENSE",
	          //   "value": "LICENSE"
	          // }
	          //   ]
            }
				]
			}
		],
		"result": {
			"header": [{label: "tl.create.groups.feematrixtype.natureofbusiness"},{label: "tl.create.groups.feematrixtype.applicationtype"}, {label: "tl.create.groups.feematrixtype.licensecategory"}, {label: "tl.create.groups.feematrixtype.subcategory"}, {label: "tl.create.groups.feematrixtype.feetype"}, {label: "tl.create.groups.feematrixtype.effectivefinancialyear"}],
			"values": ["businessNature","applicationType", "categoryName", "subCategoryName", "feeType", "financialYear"],
			"resultPath": "feeMatrices",
			"rowClickUrlUpdate": "/non-framework/tl/masters/updateFeeMatrix/{id}",
			"rowClickUrlView": "/non-framework/tl/masters/viewFeeMatrix/{id}"
			}
	},

  "tl.view": {
		"numCols": 12/2,
		"url": "/tl-masters/feematrix/v1/_search?ids={id}",
		"tenantIdRequired": true,
		"useTimestamp": true,
		"objectName": "feeMatrices[0]",
		"groups": [
			{
				"label": "tl.view.groups.feematrixtype.title",
				"name": "viewfeeMatrices",
				"fields": [
          {
            "name": "applicationtype",
            "jsonPath": "feeMatrices[0].applicationType",
            "label": "tl.view.groups.feematrixtype.applicationtype",
            "pattern": "",
            "type": "text",
            "url": "",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "natureofbusiness",
            "jsonPath": "feeMatrices[0].businessNature",
            "label": "tl.view.groups.feematrixtype.natureofbusiness",
            "pattern": "",
            "type": "text",
            "url": "",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "licensecategory",
            "jsonPath": "feeMatrices[0].categoryName",
            "label": "tl.view.groups.feematrixtype.licensecategory",
            "pattern": "",
            "type": "text",
            "url": "",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "subcategory",
            "jsonPath": "feeMatrices[0].subCategoryName",
            "label": "tl.view.groups.feematrixtype.subcategory",
            "pattern": "",
            "type": "text",
            "url": "",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
          {
            "name": "effectivefinancialyear",
            "jsonPath": "feeMatrices[0].financialYear",
            "label": "tl.view.groups.feematrixtype.effectivefinancialyear",
            "pattern": "",
            "type": "text",
            "url": "",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          },
					{
            "name": "feetype",
            "jsonPath": "feeMatrices[0].feeType",
            "label": "tl.view.groups.feematrixtype.feetype",
            "pattern": "",
            "type": "text",
            "url": "",
            "isRequired": true,
            "isDisabled": false,
            "requiredErrMsg": "",
            "patternErrMsg": ""
          }
				]
			}
		]
	},

	"tl.update": {
		"numCols": 12/2,
		"searchUrl": "/tl-masters/feematrix/v1/_search?ids={id}",
		"url": "/tl-masters/feematrix/v1/_update",
		"tenantIdRequired": true,
		"useTimestamp": true,
		"objectName": "feeMatrices",
		"groups": [
	    {
	      "label": "tl.update.groups.feematrixtype.title",
	      "name": "updatefeeMatrices",
	      "fields": [
	        {
	          "name": "updateapplicationtype",
	          "jsonPath": "feeMatrices[0].applicationType",
	          "label": "tl.update.groups.feematrixtype.applicationtype",
	          "pattern": "",
	          "type": "singleValueList",
	          "url": "",
	          "isRequired": false,
	          "isDisabled": true,
	          "requiredErrMsg": "",
	          "patternErrMsg": "",
	          "defaultValue": [
	          {
	            "key": "NEW",
	            "value": "NEW"
	          },
	          {
	            "key": "RENEW",
	            "value": "RENEW"
	          }
	            ]
	        },
	        {
	          "name": "updatebusinessNature",
	          "jsonPath": "feeMatrices[0].businessNature",
	          "label": "tl.update.groups.feematrixtype.natureofbusiness",
	          "pattern": "",
	          "type": "singleValueList",
	          "url": "",
	          "isRequired": false,
	          "isDisabled": true,
	          "requiredErrMsg": "",
	          "patternErrMsg": "",
	            "defaultValue": [
	          {
	            "key": "PERMANENT",
	            "value": "PERMANENT"
	          },
	          {
	            "key": "TEMPORARY",
	            "value": "TEMPORARY"
	          }
	            ]
	        },
	        {
	          "name": "updatecategoryId",
	          "jsonPath": "feeMatrices[0].category",
	          "label": "tl.update.groups.feematrixtype.licensecategory",
	          "pattern": "",
	          "type": "singleValueList",
	          "url": "/tl-masters/category/v1/_search?tenantId=default&active=true&type=category|$..code|$..name",
	          "isRequired": true,
	          "isDisabled": true,
	          "requiredErrMsg": "",
	          "patternErrMsg": "",
	            "depedants": [{
	              "jsonPath": "feeMatrices[0].subCategory",
	              "type": "dropDown",
	              "pattern": "/tl-masters/category/v1/_search?tenantId=default&active=true&type=subcategory&category={feeMatrices[0].category}|$.categories.*.code|$.categories.*.name"
	            }]
	        },
	        {
	          "name": "updatesubCategoryId",
	          "jsonPath": "feeMatrices[0].subCategory",
	          "label": "tl.update.groups.feematrixtype.subcategory",
	          "pattern": "",
	          "type": "singleValueList",
	          "url": "",
	          "isRequired": true,
	          "isDisabled": true,
	          "requiredErrMsg": "",
	          "patternErrMsg": "",
						"depedants": [
							{
								"jsonPath": "feeMatrices[0].feeType",
								"type": "dropDown",
								"pattern": "/tl-masters/category/v1/_search?tenantId=default&active=true&type=subcategory&codes={feeMatrices[0].subCategory}|$..feeType|$..feeType"
							}
						]
	        },
	        {
	          "name": "updateFeetype",
	          "jsonPath": "feeMatrices[0].feeType",
	          "label": "tl.update.groups.feematrixtype.feetype",
	          "pattern": "",
	          "type": "singleValueList",
	          "url": "",
	          "isRequired": true,
	          "isDisabled": true,
	          "requiredErrMsg": "",
	          "patternErrMsg": "",
	          //   "defaultValue": [
	          // {
	          //   "key": "LICENSE",
	          //   "value": "LICENSE"
	          // }
	          //   ]
	        },

					{
						"name": "financialYear",
						"jsonPath": "feeMatrices[0].financialYear",
						"label": "tl.update.groups.feematrixtype.effectivefinancialyear",
						"pattern": "",
						"type": "singleValueList",
						"url": "/egf-masters/financialyears/_search?tenantId=default|$..finYearRange|$..finYearRange",
						"isRequired": true,
						"isDisabled": true,
						"requiredErrMsg": "",
						"patternErrMsg": ""
					}
	      ]
	    }
	  ]
	}
}
  export default dat;
