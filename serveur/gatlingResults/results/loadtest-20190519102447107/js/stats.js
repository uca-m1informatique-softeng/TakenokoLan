var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "20087",
        "ok": "16318",
        "ko": "3769"
    },
    "minResponseTime": {
        "total": "5",
        "ok": "5",
        "ko": "5"
    },
    "maxResponseTime": {
        "total": "21720",
        "ok": "21720",
        "ko": "13251"
    },
    "meanResponseTime": {
        "total": "6312",
        "ok": "5997",
        "ko": "7678"
    },
    "standardDeviation": {
        "total": "5979",
        "ok": "6532",
        "ko": "1866"
    },
    "percentiles1": {
        "total": "5175",
        "ok": "2869",
        "ko": "8067"
    },
    "percentiles2": {
        "total": "9840",
        "ok": "11235",
        "ko": "8602"
    },
    "percentiles3": {
        "total": "18137",
        "ok": "18408",
        "ko": "10530"
    },
    "percentiles4": {
        "total": "20250",
        "ok": "20507",
        "ko": "11566"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 5313,
        "percentage": 26
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 665,
        "percentage": 3
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 10340,
        "percentage": 51
    },
    "group4": {
        "name": "failed",
        "count": 3769,
        "percentage": 19
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "213.691",
        "ok": "173.596",
        "ko": "40.096"
    }
},
contents: {
"req_tryconnect-b556b": {
        type: "REQUEST",
        name: "TryConnect",
path: "TryConnect",
pathFormatted: "req_tryconnect-b556b",
stats: {
    "name": "TryConnect",
    "numberOfRequests": {
        "total": "20087",
        "ok": "16318",
        "ko": "3769"
    },
    "minResponseTime": {
        "total": "5",
        "ok": "5",
        "ko": "5"
    },
    "maxResponseTime": {
        "total": "21720",
        "ok": "21720",
        "ko": "13251"
    },
    "meanResponseTime": {
        "total": "6312",
        "ok": "5997",
        "ko": "7678"
    },
    "standardDeviation": {
        "total": "5979",
        "ok": "6532",
        "ko": "1866"
    },
    "percentiles1": {
        "total": "5175",
        "ok": "2838",
        "ko": "8067"
    },
    "percentiles2": {
        "total": "9841",
        "ok": "11236",
        "ko": "8602"
    },
    "percentiles3": {
        "total": "18137",
        "ok": "18408",
        "ko": "10530"
    },
    "percentiles4": {
        "total": "20250",
        "ok": "20507",
        "ko": "11566"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 5313,
        "percentage": 26
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 665,
        "percentage": 3
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 10340,
        "percentage": 51
    },
    "group4": {
        "name": "failed",
        "count": 3769,
        "percentage": 19
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "213.691",
        "ok": "173.596",
        "ko": "40.096"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
