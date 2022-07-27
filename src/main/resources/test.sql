select
    *
from
    wh_category
where
        subject like '%subj%'
order by
    id limit 20,
    20