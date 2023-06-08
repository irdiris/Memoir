
// SIDEBAR DROPDOWN
const allDropdown = document.querySelectorAll('#sidebar .side-menu-dropdown');
const sidebar = document.getElementById('sidebar');

allDropdown.forEach(item => {
    const a = item.parentElement.querySelector('a:first-child');
    a.addEventListener('click', function (e) {
        e.preventDefault();

        if (!this.classList.contains('active')) {
            allDropdown.forEach(i => {
                const aLink = i.parentElement.querySelector('a:first-child');

                aLink.classList.remove('active');
                i.classList.remove('show');
            })
        }

        this.classList.toggle('active');
        item.classList.toggle('show');
    })
})

// SIDEBAR COLLAPSE
const toggleSidebar = document.querySelector('nav .toggle-sidebar');
const allSideDivider = document.querySelectorAll('#sidebar .divider');

if (sidebar.classList.contains('hide')) {
    allSideDivider.forEach(item => {
        item.textContent = '-'
    })
    allDropdown.forEach(item => {
        const a = item.parentElement.querySelector('a:first-child');
        a.classList.remove('active');
        item.classList.remove('show');
    })
} else {
    allSideDivider.forEach(item => {
        item.textContent = item.dataset.text;
    })
}

toggleSidebar.addEventListener('click', function () {
    sidebar.classList.toggle('hide');

    if (sidebar.classList.contains('hide')) {
        allSideDivider.forEach(item => {
            item.textContent = '-'
        })

        allDropdown.forEach(item => {
            const a = item.parentElement.querySelector('a:first-child');
            a.classList.remove('active');
            item.classList.remove('show');
        })
    } else {
        allSideDivider.forEach(item => {
            item.textContent = item.dataset.text;
        })
    }
})

// Handle clicking outside of the sidebar to hide it
document.addEventListener('click', function (event) {
    const sidebar = document.getElementById('sidebar');
    const toggleSidebar = document.querySelector('nav .toggle-sidebar');

    if (!sidebar.contains(event.target) && event.target !== toggleSidebar) {
        sidebar.classList.add('hide');
        allSideDivider.forEach(item => {
            item.textContent = '-'
        })
        allDropdown.forEach(item => {
            const a = item.parentElement.querySelector('a:first-child');
            a.classList.remove('active');
            item.classList.remove('show');
        })
    }
})

sidebar.addEventListener('mouseleave', function () {
    if (this.classList.contains('hide')) {
        allDropdown.forEach(item => {
            const a = item.parentElement.querySelector('a:first-child');
            a.classList.remove('active');
            item.classList.remove('show');
        })
        allSideDivider.forEach(item => {
            item.textContent = '-'
        })
    }
})

sidebar.addEventListener('mouseenter', function () {
    if (this.classList.contains('hide')) {
        allDropdown.forEach(item => {
            const a = item.parentElement.querySelector('a:first-child');
            a.classList.remove('active');
            item.classList.remove('show');
        })
        allSideDivider.forEach(item => {
            item.textContent = item.dataset.text;
        })
    }
})




// PROFILE DROPDOWN
const profile = document.querySelector('nav .profile');
const imgProfile = profile.querySelector('img');
const dropdownProfile = profile.querySelector('.profile-link');

imgProfile.addEventListener('click', function () {
    dropdownProfile.classList.toggle('show');
})

//NOTIFICATION DROPDOWN
const notif = document.querySelector('nav .notif');
const icon= notif.querySelector('#bell');
const dropdownnotif= notif.querySelector('.notif-link');

icon.addEventListener('click', function () {
    dropdownnotif.classList.toggle('show');
})




// MENU
const allMenu = document.querySelectorAll('main .content-data .head .menu');

allMenu.forEach(item=> {
    const icon = item.querySelector('.icon');
    const menuLink = item.querySelector('.menu-link');

    icon.addEventListener('click', function () {
        menuLink.classList.toggle('show');
    })
})



window.addEventListener('click', function (e) {
    if(e.target !== imgProfile) {
        if(e.target !== dropdownProfile) {
            if(dropdownProfile.classList.contains('show')) {
                dropdownProfile.classList.remove('show');
            }
        }
    }


    window.addEventListener('click', function (e) {
        if(e.target !== icon) {
            if(e.target !== dropdownnotif) {
                if(dropdownnotif.classList.contains('show')) {
                    dropdownnotif.classList.remove('show');
                }
            }
        }




        allMenu.forEach(item=> {
            const icon = item.querySelector('.icon');
            const menuLink = item.querySelector('.menu-link');

            if(e.target !== icon) {
                if(e.target !== menuLink) {
                    if (menuLink.classList.contains('show')) {
                        menuLink.classList.remove('show')
                    }
                }
            }
        })
    })

})


// APEXCHART
//CHART
var options = {
    series: [{
        name: 'Equipments',
        data: [security.valueOf(), robotics.valueOf(),computers.valueOf(), network.valueOf(),hpc.valueOf()]
    }],
    chart: {
        type: 'bar',
        height: 350
    },
    plotOptions: {
        bar: {
            horizontal: false,
            columnWidth: '55%',
            endingShape: 'rounded'
        },
    },
    dataLabels: {
        enabled: false
    },
    stroke: {
        show: true,
        width: 2,
        colors: ['transparent']
    },
    xaxis: {
        categories: ['Security', 'Robotics', 'Computers', 'Network', 'HPC' ],
    },
    yaxis: {
        title: {
            text: 'Number of Equipments'
        }
    },
    fill: {
        opacity: 1
    },
    tooltip: {
        y: {
            formatter: function (val) {
                return val + " Equipment"
            }
        }
    }
};

var chart = new ApexCharts(document.querySelector("#chart"), options);
chart.render();

//CHART1
var options = {
    series: [available.valueOf(), missing.valueOf(), allocated.valueOf(), underMaintenance.valueOf(), pending.valueOf()],
    chart: {
        width: 380,
        type: 'pie',
    },
    labels: ['Available', 'Missing', 'Allocated', 'Under Maintenance', 'Pending'],
    responsive: [{
        breakpoint: 480,
        options: {
            chart: {
                width: 200
            },
            legend: {
                position: 'bottom'
            }
        }
    }]
};

var chart1 = new ApexCharts(document.querySelector("#chart1"), options);
chart1.render();


async function banResearcher(button) {
    var row = button.parentNode.parentNode.parentNode;
    console.log(row.querySelector('td:nth-child(1)').textContent);
    console.log(row.querySelector('td:nth-child(8)').textContent);
    // Get the values of the two tds.
    const id = row.querySelector('td:nth-child(1)').textContent;
    const state = row.querySelector('td:nth-child(8)').textContent;
    var user = {
        id: id,
        state: state
    };
    // Create a new Request object.
    const request = new Request("/AllocationManager/allocationManager/BanResearcher", {
        headers: {
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify(user)
    });
    try {
        // Send the request.
        const response = await fetch(request);

        // Handle the response.
        if (response.status === 200) {
            // The request was successful.
            console.log("The request was successful.");
            location.reload();
        } else {
            // The request failed.
            console.log("The request failed.");
        }
    } catch (error) {
        console.log(error);
    }

}
async function deleteResearcher(button) {
    var row = button.parentNode.parentNode.parentNode;

    // Get the values of the two tds.
    const id = row.querySelector('td:nth-child(1)').textContent;
    console.log(id)
    var user = {
        id: id,
    };
    // Create a new Request object.
    const request = new Request("/AllocationManager/AllocationManager/DeleteResearcher", {
        headers: {
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify(user)
    });
    try {
        // Send the request.
        const response = await fetch(request);

        // Handle the response.
        if (response.status === 200) {
            // The request was successful.
            console.log("The request was successful.");
            location.reload();
        } else {
            // The request failed.
            console.log("The request failed.");
        }
    } catch (error) {
        console.log(error);
    }

}

async function banStudent(button) {
    var row = button.parentNode.parentNode.parentNode;
    console.log(row.querySelector('td:nth-child(1)').textContent);
    console.log(row.querySelector('td:nth-child(8)').textContent);
    // Get the values of the two tds.
    const id = row.querySelector('td:nth-child(1)').textContent;
    const state = row.querySelector('td:nth-child(8)').textContent;
    var user = {
        id: id,
        state: state
    };
    // Create a new Request object.
    const request = new Request("/AllocationManager/allocationManager/BanStudent", {
        headers: {
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify(user)
    });
    try {
        // Send the request.
        const response = await fetch(request);

        // Handle the response.
        if (response.status === 200) {
            // The request was successful.
            console.log("The request was successful.");
            location.reload();
        } else {
            // The request failed.
            console.log("The request failed.");
        }
    } catch (error) {
        console.log(error);
    }

}
async function deleteStudent(button) {
    var row = button.parentNode.parentNode.parentNode;

    // Get the values of the two tds.
    const id = row.querySelector('td:nth-child(1)').textContent;
    console.log(id)
    var user = {
        id: id,
    };
    // Create a new Request object.
    const request = new Request("/AllocationManager/AllocationManager/DeleteStudent", {
        headers: {
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify(user)
    });
    try {
        // Send the request.
        const response = await fetch(request);

        // Handle the response.
        if (response.status === 200) {
            // The request was successful.
            console.log("The request was successful.");
            location.reload();
        } else {
            // The request failed.
            console.log("The request failed.");
        }
    } catch (error) {
        console.log(error);
    }

}
async function AcceptRequest(button) {
    var row = button.parentNode.parentNode.parentNode;
   console.log(row);
    // Get the values of the two tds.
    const userId = row.querySelector('td:nth-child(1)').textContent;
    const itemId = row.querySelector('td:nth-child(2)').textContent;
    var equipmentRequest = {
        userId: userId,
        itemId: itemId
    };
    console.log(equipmentRequest.itemId)
    // Create a new Request object.
    const request = new Request("/AllocationManager/AllocationManager/AcceptRequest", {
        headers: {
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify(equipmentRequest)
    });
    try {
        // Send the request.
        const response = await fetch(request);

        // Handle the response.
        if (response.status === 200) {
            // The request was successful.
            console.log("The request was successful.");
            location.reload();
        } else {
            // The request failed.
            console.log("The request failed.");
        }
    } catch (error) {
        console.log(error);
    }

}
async function RefuseRequest(button) {
    var row = button.parentNode.parentNode.parentNode;
    console.log(row);
    // Get the values of the two tds.
    const userId = row.querySelector('td:nth-child(1)').textContent;
    const itemId = row.querySelector('td:nth-child(2)').textContent;
    var equipmentRequest = {
        userId: userId,
        itemId: itemId
    };
    console.log(equipmentRequest.itemId)
    // Create a new Request object.
    const request = new Request("/AllocationManager/AllocationManager/RefuseRequest", {
        headers: {
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify(equipmentRequest)
    });
    try {
        // Send the request.
        const response = await fetch(request);

        // Handle the response.
        if (response.status === 200) {
            // The request was successful.
            console.log("The request was successful.");
            location.reload();
        } else {
            // The request failed.
            console.log("The request failed.");
        }
    } catch (error) {
        console.log(error);
    }

}

async function RefuseHPCRequest(button) {
    var row = button.parentNode.parentNode.parentNode;
    console.log(row);
    // Get the values of the two tds.
    const userId = row.querySelector('td:nth-child(1)').textContent;
    const itemId = row.querySelector('td:nth-child(2)').textContent;
    const dateOfAcquisition = row.querySelector('td:nth-child(3)').textContent;
    var HPCRequest = {
        userId: userId,
        itemId: itemId,
        dateOfAcquisition:dateOfAcquisition

    };

    // Create a new Request object.
    const request = new Request("/AllocationManager/AllocationManager/RefuseHPCRequest", {
        headers: {
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify(HPCRequest)
    });
    try {
        // Send the request.
        const response = await fetch(request);

        // Handle the response.
        if (response.status === 200) {
            // The request was successful.
            console.log("The request was successful.");
            location.reload();
        } else {
            // The request failed.
            console.log("The request failed.");
        }
    } catch (error) {
        console.log(error);
    }

}

async function AcceptHPCRequest(button) {
    var row = button.parentNode.parentNode.parentNode;
    console.log(row);
    // Get the values of the two tds.
    const userId = row.querySelector('td:nth-child(1)').textContent;
    const itemId = row.querySelector('td:nth-child(2)').textContent;
    const dateOfAcquisition = row.querySelector('td:nth-child(3)').textContent;
    var HPCRequest = {
        userId: userId,
        itemId: itemId,
        dateOfAcquisition:dateOfAcquisition

    };

    // Create a new Request object.
    const request = new Request("/AllocationManager/AllocationManager/AcceptHPCRequest", {
        headers: {
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify(HPCRequest)
    });
    try {
        // Send the request.
        const response = await fetch(request);

        // Handle the response.
        if (response.status === 200) {
            // The request was successful.
            console.log("The request was successful.");
            location.reload();
        } else {
            // The request failed.
            console.log("The request failed.");
        }
    } catch (error) {
        console.log(error);
    }

}
async function MarkHPCRequest(button) {
    var row = button.parentNode.parentNode.parentNode;
    console.log(row);
    // Get the values of the two tds.
    const userId = row.querySelector('td:nth-child(1)').textContent;
    const itemId = row.querySelector('td:nth-child(2)').textContent;
    const dateOfAcquisition = row.querySelector('td:nth-child(3)').textContent;
    var HPCRequest = {
        userId: userId,
        itemId: itemId,
        dateOfAcquisition:dateOfAcquisition

    };

    // Create a new Request object.
    const request = new Request("/AllocationManager/AllocationManager/MarkHPCRequest", {
        headers: {
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify(HPCRequest)
    });
    try {
        // Send the request.
        const response = await fetch(request);

        // Handle the response.
        if (response.status === 200) {
            // The request was successful.
            console.log("The request was successful.");
            location.reload();
        } else {
            // The request failed.
            console.log("The request failed.");
        }
    } catch (error) {
        console.log(error);
    }}
async function MarkEquipment(button) {
    var row = button.parentNode.parentNode.parentNode;
    console.log(row);
    // Get the values of the two tds.
    const userId = row.querySelector('td:nth-child(1)').textContent;
    const itemId = row.querySelector('td:nth-child(2)').textContent;
    const dateOfAcquisition = row.querySelector('td:nth-child(3)').textContent;
    var HPCRequest = {
        userId: userId,
        itemId: itemId,
        dateOfAcquisition:dateOfAcquisition

    };

    // Create a new Request object.
    const request = new Request("/AllocationManager/AllocationManager/MarkEquipment", {
        headers: {
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify(HPCRequest)
    });
    try {
        // Send the request.
        const response = await fetch(request);

        // Handle the response.
        if (response.status === 200) {
            // The request was successful.
            console.log("The request was successful.");
            location.reload();
        } else {
            // The request failed.
            console.log("The request failed.");
        }
    } catch (error) {
        console.log(error);
    }}