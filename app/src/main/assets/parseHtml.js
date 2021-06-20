function getSectionTimes(str_sectionTimes, index) {
    let sectionTimes = {};
    str_sectionTimes = (str_sectionTimes.replace("第", "")).replace("节(", " ");
    str_sectionTimes = (str_sectionTimes.replace("-", " ")).replace(")", "");
    let sec = str_sectionTimes.split(" ");
    sectionTimes.section = index + 1;
    sectionTimes.startTime = sec[1];
    sectionTimes.endTime = sec[2];
    return sectionTimes;
}

function getWeeks(str_weeks) {
    let weeks = [];
    let flag = 0;
    if (str_weeks.search("单") != -1) {
        flag = 1;
        str_weeks = str_weeks.replace("单", "");
    } else if (str_weeks.search("双") != -1) {
        flag = 2;
        str_weeks = str_weeks.replace("双", "");
    }
    str_weeks = str_weeks.replace("周", "");
    let startWeek = Number(str_weeks.split('-')[0]);
    let endWeek = Number(str_weeks.split('-')[1]);
    for (let i = startWeek; i <= endWeek; i++) {
        if (flag == 0) {
            weeks.push(i);
        } else if (flag == 1 && i % 2 == 1) {
            weeks.push[i];
        } else if (flag == 2 && i % 2 == 0) {
            weeks.push(i);
        }
    }
    return weeks;
}

function getSections(str_sections) {
    let sections = [];
    str_sections = str_sections.replace("节", "");
    let startSection = Number(str_sections.split("-")[0]);
    let endSection = Number(str_sections.split("-")[1]);
    for (let i = startSection; i <= endSection; i++) {
        sections.push({
            section: i
        });
    }
    return sections;
}


function parseHtml() {

    let courseTable = document.querySelector("#courseTableBody");
    let trs = courseTable.querySelectorAll("tr");
    let sectionTimes = [];
    let courseInfos = [];

    for (let i = 0; i < trs.length; i++) {
        let str_sectionTimes = (trs[i].querySelectorAll("th:last-of-type"))[0].innerText;
        sectionTimes.push(getSectionTimes(str_sectionTimes, i));
        let tds = trs[i].querySelectorAll("td");

        for (let j = 0; j < tds.length; j++) {
            let result = {};
            let td_context = tds[j].querySelectorAll("p");
            if (td_context.length != 0) {
                result.name = td_context[0].innerText;
                result.teacher = td_context[1].innerText;
                let str_weeks = td_context[2].innerText;
                result.weeks = getWeeks(str_weeks);
                let str_sections = td_context[3].innerText;
                result.sections = getSections(str_sections);
                result.position = td_context[4].innerText;
                result.day = j + 1;
                courseInfos.push(result);
            }
        }
    }
    return {
        sectionTimes: sectionTimes,
        courseInfos: courseInfos
    }
}