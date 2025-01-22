local currentBalanceStr = redis.call('GET', KEYS[1])
if not currentBalanceStr then
    return "-1"  -- User not found, return as string
end

local currentBalance = tonumber(currentBalanceStr)
local amount = tonumber(ARGV[1])
currentBalance = currentBalance + amount
redis.call('SET', KEYS[1], tostring(currentBalance))

return tostring(currentBalance)
